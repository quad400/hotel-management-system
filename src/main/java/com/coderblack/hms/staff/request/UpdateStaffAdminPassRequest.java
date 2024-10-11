package com.coderblack.hms.staff.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateStaffAdminPassRequest(
        @NotBlank(message = "Position is required")
        @NotNull(message = "Position is required")
        String position,
        @NotBlank(message = "Salary is required")
        @NotNull(message = "Salary is required")
        BigDecimal salary
) {
}
