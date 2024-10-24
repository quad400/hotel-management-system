package com.coderblack.hms.feedback;

import com.coderblack.hms.user.UserResponse;

import java.time.LocalDateTime;

public record FeedbackResponse(
        String id,
        Float rate,
        String comment,
        UserResponse user,
        LocalDateTime createAt,
        LocalDateTime updatedAt

) {
}
