package com.coderblack.hms.feedback.request;

public record UpdateFeedbackRequest(
        Float rate,
        String comment
) {
}
