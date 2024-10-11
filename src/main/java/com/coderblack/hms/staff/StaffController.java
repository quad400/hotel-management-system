package com.coderblack.hms.staff;

import com.coderblack.hms.common.response.DefaultResponse;
import com.coderblack.hms.staff.request.AddStaffRequest;
import com.coderblack.hms.staff.request.UpdateStaffAdminPassRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequestMapping("/staffs")
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;

    @PreAuthorize("hasRole'(ADMIN)'")
    @PostMapping("/add")
    public ResponseEntity<DefaultResponse> addStaff(
            @RequestBody @Valid AddStaffRequest request
    ) throws AccountNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(staffService.addStaff(request));
    }

    @PreAuthorize("hasRole'(ADMIN)'")
    @PostMapping("/update-staff-admin-pass/{staffId}")
    public ResponseEntity<DefaultResponse> updateStaffAdminPass(
            @RequestBody @Valid UpdateStaffAdminPassRequest request,
            @PathVariable("staffId") String staffId
    ) throws AccountNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(staffService.updateStaffAdminPass(request, staffId));
    }


}
