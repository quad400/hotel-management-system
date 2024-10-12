package com.coderblack.hms.staff.request;

import com.coderblack.hms.common.contraint.EnumConstraint;
import com.coderblack.hms.staff.Position;
import com.coderblack.hms.user.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

import static com.coderblack.hms.staff.Position.*;
import static com.coderblack.hms.staff.Position.FINANCE;

public record AddStaffRequest(
        @NotNull(message = "userId is required")
        @NotBlank(message = "userId is required")
        String userId,

        @EnumConstraint(enumClass = Position.class, message = "Invalid request position:. expects  CHEF | FINANCE | HOUSEKEEPER | HUMAN_RESOURCE | MAINTENANCE | MANAGER | RECEPTIONIST | SALES_AND_MARKETING | SECURITY")
        String position,

        @NotNull(message = "salary is required")
        BigDecimal salary
) {
}
