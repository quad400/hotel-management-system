package com.coderblack.hms.room.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateReservationRequest(
        @NotNull(message = "roomId is required")
        @NotBlank(message = "roomId is required")
        String roomId,

        @NotNull(message = "checkInDate is required")
        LocalDate checkInDate,

        @NotNull(message = "checkOutDate is required")
        LocalDate checkOutDate
) {
}
