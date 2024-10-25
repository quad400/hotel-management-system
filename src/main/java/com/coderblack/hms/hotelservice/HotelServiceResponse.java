package com.coderblack.hms.hotelservice;

import com.coderblack.hms.common.contraint.HotelServiceCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HotelServiceResponse(
        String id,
        String name,
        BigDecimal price,
        HotelServiceCategory serviceCategory,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
