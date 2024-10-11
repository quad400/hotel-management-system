package com.coderblack.hms.auth.request;

import com.coderblack.hms.common.contraint.EnumConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ActivateUserRequest {

    @EnumConstraint(enumClass = RequestTypeEnum.class, message = "Invalid request type. expects REGISTER | RESET_PASSWORD")
    private String type;

    @NotNull(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;

    @NotNull(message = "Token is required")
    @NotBlank(message = "Token is required")
    private String token;

}
