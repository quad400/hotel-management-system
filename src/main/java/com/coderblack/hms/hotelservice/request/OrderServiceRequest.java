package com.coderblack.hms.hotelservice.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderServiceRequest(
        @NotNull(message = "hotelService is required")
        @NotBlank(message = "hotelService is required")
        String hotelService,
        @NotNull(message = "price is required")
        BigDecimal price,
        @NotNull(message = "details is required")
        @NotBlank(message = "details is required")
        String details
) {


}
