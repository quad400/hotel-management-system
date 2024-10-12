package com.coderblack.hms.staff;

import com.coderblack.hms.common.response.BaseResponse;
import com.coderblack.hms.common.response.DefaultResponse;
import com.coderblack.hms.common.response.PageResponse;
import com.coderblack.hms.exception.NotFoundException;
import com.coderblack.hms.exception.UnauthorizedException;
import com.coderblack.hms.exception.ConflictException;
import com.coderblack.hms.staff.request.AddStaffRequest;
import com.coderblack.hms.staff.request.UpdateStaffAdminPassRequest;
import com.coderblack.hms.staff.request.UpdateStaffRequest;
import com.coderblack.hms.user.Role;
import com.coderblack.hms.user.User;
import com.coderblack.hms.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class StaffService {
    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private final StaffMapper staffMapper;

    public DefaultResponse addStaff(@Valid AddStaffRequest request) throws AccountNotFoundException {
        User user = userRepository.findById(request.userId()).orElseThrow(() -> new AccountNotFoundException("Account not found"));

        staffRepository.findByUser(user).ifPresent(existingStaff -> {
            throw new ConflictException("User with ID:" + request.userId() + " is already a staff");
        });

        Staff staff = Staff.builder()
                .user(user)
                .salary(request.salary())
                .position(Position.valueOf(request.position()))
                .staffId(generateStaffId(5))
                .build();

        staffRepository.save(staff);
        user.setRole(Role.STAFF);
        userRepository.save(user);
        return new DefaultResponse("User added to Staff list successfully", staff.getId());
    }


    public String generateStaffId(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return "Staff_" + codeBuilder.toString();

    }

    public DefaultResponse updateStaffAdminPass(@Valid UpdateStaffAdminPassRequest request, String staffId) {
        Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new NotFoundException(String.format("Staff with ID:%s not found", staffId)));

        staffMapper.toStaff(request, staff);
        staffRepository.save(staff);

        return new DefaultResponse("Staff Updated successfully", staff.getId());
    }

    public DefaultResponse updateStaff(@Valid UpdateStaffRequest request, String staffId, Authentication connectedUser) {
        String userId = ((User) connectedUser.getPrincipal()).getId();
        Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new NotFoundException(String.format("Staff with ID:%s not found", staffId)));

        if(!staff.getUser().getId().equals(userId)){
            throw new UnauthorizedException("You cannot update personal information of another user");
        }
        staffMapper.toStaffUpdate(request, staff);
        staffRepository.save(staff);

        return new DefaultResponse("Staff Updated successfully", staff.getId());
    }

    public BaseResponse<StaffResponse> getStaff(String staffId) {
        StaffResponse staff = staffRepository.findById(staffId)
                .map(staffMapper::toStaffResponse)
                .orElseThrow(() -> new NotFoundException(String.format("Staff with ID:%s not found", staffId)));
        return new BaseResponse<StaffResponse>("Staff fetched successfully", staff);
    }

    public PageResponse<StaffResponse> getStaffs(String search, Pageable pageable) {
        Page<Staff> staffs;
        if (Objects.isNull(search) || search.isEmpty()) {
            staffs = staffRepository.findAll(pageable);
        } else {
            staffs = staffRepository.findByStaffIdContainingIgnoreCaseOrPositionContainingIgnoreCaseOrSalaryContainingIgnoreCase(search, search, search, pageable);
        }

        List<StaffResponse> staffResponses = staffs.stream()
                .map(staffMapper::toStaffResponse)
                .toList();
        return new PageResponse<>("Staffs Fetched Successfully",
                staffs.getNumber() + 1,
                staffs.getTotalElements(),
                staffs.getSize(),
                staffs.hasPrevious(),
                staffs.hasNext(),
                staffResponses
        );
    }
}
