package com.coderblack.hms.room;

import com.coderblack.hms.room.enums.ReservationStatus;
import com.coderblack.hms.user.UserResponse;

import java.time.LocalDate;

public record ReservationResponse(
        String id,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        ReservationStatus status,
        UserResponse user,
        RoomResponse room
) {
}
