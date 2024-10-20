package com.coderblack.hms.room.request;

import com.coderblack.hms.auth.request.RequestTypeEnum;
import com.coderblack.hms.common.contraint.EnumConstraint;
import com.coderblack.hms.room.enums.RoomStatus;
import com.coderblack.hms.room.enums.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateRoomRequest(
        String roomNumber,
        int capacity,
        BigDecimal price,
        @EnumConstraint(enumClass = RoomType.class, message = "Invalid request roomType. expects SINGLE | DOUBLE | SUITE | DELUXE | FAMILY")
        String roomType,
        @EnumConstraint(enumClass = RoomStatus.class, message = "Invalid request roomStatus. expects AVAILABLE | OCCUPIED | UNDER_MAINTENANCE | CLEANING")
        String roomStatus,
        String description
) {
}
