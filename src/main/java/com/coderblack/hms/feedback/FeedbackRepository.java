package com.coderblack.hms.feedback;

import com.coderblack.hms.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, String> {
    Optional<Feedback> findByIdAndUser(String feedbackId, User user);
}
