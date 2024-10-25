package com.coderblack.hms.room.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateReservationRequest(
        LocalDate checkInDate,
        LocalDate checkOutDate
) {
}
