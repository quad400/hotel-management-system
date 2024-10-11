package com.coderblack.hms.auth;


import com.coderblack.hms.auth.request.ActivateUserRequest;
import com.coderblack.hms.auth.request.LoginRequest;
import com.coderblack.hms.auth.request.RegisterRequest;
import com.coderblack.hms.auth.request.ResendTokenRequest;
import com.coderblack.hms.common.response.DefaultResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DefaultResponse> registerUser(
            @Valid @RequestBody RegisterRequest request
    ) throws AccountNotFoundException {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<DefaultResponse> loginUser(
            @Valid @RequestBody LoginRequest request
    ) throws AccountNotFoundException {
        return ResponseEntity.ok(authService.loginUser(request));
    }

    @PostMapping("/activate-account")
    public ResponseEntity<DefaultResponse> activateAccount(
            @Valid @RequestBody ActivateUserRequest request
    ) {
        return ResponseEntity.ok(authService.activateAccount(request));
    }

    @GetMapping("/resend-account-token")
    public ResponseEntity<DefaultResponse> resendAccountToken(
            @Valid @RequestBody ResendTokenRequest request
    ) throws AccountNotFoundException {
        return ResponseEntity.ok(authService.resendToken(request));
    }
}
