package com.coderblack.hms.staff;

import com.coderblack.hms.common.response.DefaultResponse;
import com.coderblack.hms.exception.NotFoundException;
import com.coderblack.hms.exception.UserConflictException;
import com.coderblack.hms.staff.request.AddStaffRequest;
import com.coderblack.hms.staff.request.UpdateStaffAdminPassRequest;
import com.coderblack.hms.user.User;
import com.coderblack.hms.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.security.SecureRandom;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StaffService {
    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
//    private final StaffMapper staffMapper;

    public DefaultResponse addStaff(@Valid AddStaffRequest request) throws AccountNotFoundException {
        User user = userRepository.findById(request.userId()).orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (user.getStaff().getUser().getId().equals(request.userId())) {
            throw new UserConflictException("User with ID:%s is already a staff" + request.userId());
        }

        Staff staff = Staff.builder()
                .user(user)
                .salary(request.salary())
                .position(Position.valueOf(request.position()))
                .staffId(generateStaffId(8))
                .build();

        staffRepository.save(staff);

        return new DefaultResponse("User added to Staff list successfully", staff.getId());
    }


    public String generateStaffId(int length){
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return "Staff_%s" + codeBuilder.toString();

    }

    public DefaultResponse updateStaffAdminPass(@Valid UpdateStaffAdminPassRequest request, String staffId) {
        Staff staff = staffRepository.findById(staffId).orElseThrow(()-> new NotFoundException("Staff with ID:%s not found"+ staffId));


    }

}
