package com.coderblack.hms.staff.request;

import java.math.BigDecimal;

public record UpdateStaffAdminPassRequest(
        String position,
        BigDecimal salary
) {
}
