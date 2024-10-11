package com.coderblack.hms.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequest{
        @NotNull(message = "Email is required")
        @Email(message = "Enter a valid email address")
        String email;

        @Size(min = 8, message = "Password must be greater than 6 characters")
        @NotEmpty(message = "Password is required")
        @NotNull(message = "Password is required")
        String password;
}
