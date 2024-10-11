package com.coderblack.hms.user;

import com.coderblack.hms.common.response.BaseResponse;
import com.coderblack.hms.common.response.DefaultResponse;
import com.coderblack.hms.common.response.PageResponse;
import com.coderblack.hms.user.request.UserPermissionRequest;
import com.coderblack.hms.user.request.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> getUser(
            Authentication currentUser
    ) throws AccountNotFoundException {
        var userId = ((User) currentUser.getPrincipal()).getId();

        return ResponseEntity.ok(userService.getUser(userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse<UserResponse>> getUserById(
            @PathVariable("userId") String userId
    ) throws AccountNotFoundException {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PatchMapping("/update-me")
    public ResponseEntity<DefaultResponse> updateUser(
            @RequestBody @Valid UserRequest request,
            Authentication currentUser
    ) throws AccountNotFoundException {
        return ResponseEntity.ok(userService.updateUser(request, currentUser));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<PageResponse<UserResponse>> getUsers(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "firstName") String sort
    ) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(sort));
        return ResponseEntity.ok(userService.getUsers(search, pageable));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/block-unblock-user/{userId}")
    public ResponseEntity<DefaultResponse> blockUser(
            @PathVariable("userId") String userId
    ) throws AccountNotFoundException {
        return ResponseEntity.ok(userService.blockUser(userId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update-user-permission/{userId}")
    public ResponseEntity<DefaultResponse> updateUserPermissions(
            @PathVariable("userId") String userId,
            @RequestBody @Valid UserPermissionRequest request
    ) throws AccountNotFoundException {
        return ResponseEntity.ok(userService.updateUserPermissions(userId, request));
    }

}
