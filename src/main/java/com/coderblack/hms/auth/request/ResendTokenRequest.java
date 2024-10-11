package com.coderblack.hms.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ResendTokenRequest {
    @NotNull(message = "Email is required")
    @Email(message = "Enter a valid email address")
    String email;
}
