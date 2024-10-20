package com.coderblack.hms.hotelservice.response;

import com.coderblack.hms.common.contraint.GuestHotelServiceStatus;
import com.coderblack.hms.hotelservice.HotelServiceResponse;
import com.coderblack.hms.user.UserResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GuestHotelServiceResponse(
        String id,
        String details,
        BigDecimal price,
        GuestHotelServiceStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        UserResponse user,
        HotelServiceResponse hotelService
) {
}
