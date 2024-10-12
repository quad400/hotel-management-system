package com.coderblack.hms.staff;

import com.coderblack.hms.common.response.BaseResponse;
import com.coderblack.hms.common.response.DefaultResponse;
import com.coderblack.hms.common.response.PageResponse;
import com.coderblack.hms.staff.request.AddStaffRequest;
import com.coderblack.hms.staff.request.UpdateStaffAdminPassRequest;
import com.coderblack.hms.staff.request.UpdateStaffRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/staffs")
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<DefaultResponse> addStaff(
            @RequestBody @Valid AddStaffRequest request
    ) throws AccountNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(staffService.addStaff(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-staff-admin-pass/{staffId}")
    public ResponseEntity<DefaultResponse> updateStaffAdminPass(
            @RequestBody @Valid UpdateStaffAdminPassRequest request,
            @PathVariable("staffId") String staffId
    ) throws AccountNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(staffService.updateStaffAdminPass(request, staffId));
    }

    @PreAuthorize("hasAuthority('staff:update')")
    @PutMapping("/update-staff/{staffId}")
    public ResponseEntity<DefaultResponse> updateStaff(
            @RequestBody @Valid UpdateStaffRequest request,
            @PathVariable("staffId") String staffId,
            Authentication connectedUser
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(staffService.updateStaff(request, staffId, connectedUser));
    }

    @GetMapping("/get-staff/{staffId}")
    public ResponseEntity<BaseResponse<StaffResponse>> getStaff(
            @PathVariable("staffId") String staffId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(staffService.getStaff(staffId));
    }

    @GetMapping("/get-staffs")
    public ResponseEntity<PageResponse<StaffResponse>> getStaffs(
               @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "salary") String sort
    ) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(sort));
        return ResponseEntity.status(HttpStatus.OK).body(staffService.getStaffs(search, pageable));
    }
}
