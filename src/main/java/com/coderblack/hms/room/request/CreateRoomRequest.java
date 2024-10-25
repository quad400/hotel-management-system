package com.coderblack.hms.room.request;

import com.coderblack.hms.auth.request.RequestTypeEnum;
import com.coderblack.hms.common.contraint.EnumConstraint;
import com.coderblack.hms.room.enums.RoomStatus;
import com.coderblack.hms.room.enums.RoomType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateRoomRequest(

        @NotNull(message = "roomNumber is required")
        @NotBlank(message = "rooNumber is required")
        String roomNumber,

        @Positive(message = "capacity is required and must be positive number")
        @Min(value = 1, message = "capacity must be at least 1")
        int capacity,
        @NotNull(message = "price is required")
        BigDecimal price,
        @EnumConstraint(enumClass = RoomType.class, message = "Invalid request roomType. expects SINGLE | DOUBLE | SUITE | DELUXE | FAMILY")
        @NotNull(message = "roomType is required")
        String roomType,
        @EnumConstraint(enumClass = RoomStatus.class, message = "Invalid request roomStatus. expects AVAILABLE | OCCUPIED | UNDER_MAINTENANCE | CLEANING")
        @NotNull(message = "roomStatus is required")
        String roomStatus,
        String description
) {
}
