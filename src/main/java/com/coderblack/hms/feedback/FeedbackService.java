package com.coderblack.hms.feedback;

import com.coderblack.hms.common.response.BaseResponse;
import com.coderblack.hms.common.response.DefaultResponse;
import com.coderblack.hms.common.response.PageResponse;
import com.coderblack.hms.common.response.SearchResponse;
import com.coderblack.hms.common.utils.GenericSearch;
import com.coderblack.hms.common.utils.SortDirection;
import com.coderblack.hms.exception.NotFoundException;
import com.coderblack.hms.feedback.request.CreateFeedbackRequest;
import com.coderblack.hms.feedback.request.UpdateFeedbackRequest;
import com.coderblack.hms.room.ReservationResponse;
import com.coderblack.hms.room.entity.Reservation;
import com.coderblack.hms.user.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public class FeedbackService {

    private FeedbackRepository feedbackRepository;
    private FeedbackMapper feedbackMapper;
    private GenericSearch<Feedback> genericSearch;


    public DefaultResponse addFeedback(CreateFeedbackRequest request, Authentication connectedUser) {

        User user = ((User) connectedUser.getPrincipal());

        var createdFeedback = this.feedbackMapper.createMapper(request, user);
        Feedback feedback = this.feedbackRepository.save(createdFeedback);

        return new DefaultResponse("Feedback for hotel added successfully", feedback.getId());
    }


    public DefaultResponse updateFeedback(UpdateFeedbackRequest request, Authentication connectedUser, String feedbackId) {
        User user = ((User) connectedUser.getPrincipal());

        Feedback feedback = this.feedbackRepository.findByIdAndUser(feedbackId, user).orElseThrow(()-> new NotFoundException(String.format("Feedback with ID::%s not found", feedbackId)));
        this.feedbackMapper.updateMapper(request);
        this.feedbackRepository.save(feedback);

        return new DefaultResponse("Feedback for hotel updated successfully", feedback.getId());
    }


    public DefaultResponse deleteFeedback(Authentication connectedUser, String feedbackId) {
        User user = ((User) connectedUser.getPrincipal());

        Feedback feedback = this.feedbackRepository.findByIdAndUser(feedbackId, user).orElseThrow(()-> new NotFoundException(String.format("Feedback with ID::%s not found", feedbackId)));
        this.feedbackRepository.delete(feedback);

        return new DefaultResponse("Feedback for hotel deleted successfully", feedback.getId());
    }


    public BaseResponse<FeedbackResponse> getFeedback(String feedbackId) {

        FeedbackResponse feedback = this.feedbackRepository.findById(feedbackId).map(feedbackMapper::toResponse).orElseThrow(()-> new NotFoundException(String.format("Feedback with ID::%s not found", feedbackId)));

        return new BaseResponse<>("Fetched feedback successfully", feedback);
    }


    public PageResponse<FeedbackResponse> getFeedbacks(String search, int page, int limit, String sortField, SortDirection sortDirection, Authentication connectedUser) {
        String[] feedbackFields = {"rate", "comment"}; // Only include fields from Reservation
        String[] userJoinFields = {"firstName", "lastName", "email"}; // Fields from User entity
        String[] joinFields = {"user"};

        String userId = ((User) connectedUser.getPrincipal()).getId();

        SearchResponse<Feedback> feedbacks;
        feedbacks = genericSearch.search(
                search, Feedback.class,
                page, limit,
                feedbackFields, joinFields,
                userJoinFields, sortField, sortDirection,userId
        );

        List<FeedbackResponse> response = feedbacks.results()
                .stream().map(feedbackMapper::toResponse).toList();
        int currentPage = page + 1;
        long totalCount = feedbacks.totalCount();
        int totalPages = (int) Math.ceil((double) totalCount / limit);

        return new PageResponse<>(
                "Reservations Fetched Successfully.",
                currentPage,
                totalCount,
                totalPages,
                currentPage > 1,
                currentPage < totalPages,
                response
        );
    }

}
