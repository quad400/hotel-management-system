package com.coderblack.hms.staff;

import com.coderblack.hms.user.User;
import com.coderblack.hms.user.UserResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

public record StaffResponse(
        String id,
        String address,
        LocalDate dateOfBirth,
        String staffId,
        Position position,
        BigDecimal salary,
        LocalDate dateOfJoining,
        UserResponse user

) {
}
