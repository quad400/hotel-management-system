package com.coderblack.hms.hotelservice.request;

import com.coderblack.hms.common.contraint.EnumConstraint;
import com.coderblack.hms.common.contraint.HotelServiceCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateServiceRequest(
        BigDecimal price,
        String name,
        @EnumConstraint(enumClass = HotelServiceCategory.class, message = "Invalid request serviceCategory: ROOM_SERVICE | GUEST_SERVICE | FOOD_AND_BEVERAGES_SERVICE | WELLNESS_AND_RECREATION_SERVICE | BUSINESS_SERVICE | EVENT_AND_ENTERTAINMENT_SERVICE | LUXURY_AND_VIP_SERVICE | HEALTH_AND_SAFETY_SERVICE | SPECIALTY_SERVICE | ROOM_SPECIFIC_ADD_ON | GUEST_LOYALTY_PROGRAMS | TRANSPORT_SERVICE")
        String serviceCategory
) {
}
