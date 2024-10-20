package com.coderblack.hms.room;

import com.coderblack.hms.room.enums.RoomStatus;
import com.coderblack.hms.room.enums.RoomType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RoomResponse(
        String roomNumber,
        int capacity,
        BigDecimal price,
        RoomType roomType,
        RoomStatus roomStatus,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
