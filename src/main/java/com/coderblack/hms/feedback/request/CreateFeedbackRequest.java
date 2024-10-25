package com.coderblack.hms.feedback.request;

import jakarta.validation.constraints.NotNull;

public record CreateFeedbackRequest(
        @NotNull(message = "rate is required")
        Float rate,
        String comment
) {
}
