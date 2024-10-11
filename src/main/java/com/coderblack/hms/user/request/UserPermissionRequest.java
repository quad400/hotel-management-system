package com.coderblack.hms.user.request;

import com.coderblack.hms.common.contraint.EnumConstraint;
import com.coderblack.hms.user.Role;

public record UserPermissionRequest(
        @EnumConstraint(enumClass = Role.class, message = "Invalid request type. expects REGISTER | RESET_PASSWORD")
        String role

) {
}
