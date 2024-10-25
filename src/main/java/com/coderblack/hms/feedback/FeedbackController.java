package com.coderblack.hms.feedback;


import com.coderblack.hms.common.response.BaseResponse;
import com.coderblack.hms.common.response.DefaultResponse;
import com.coderblack.hms.common.response.PageResponse;
import com.coderblack.hms.common.utils.SortDirection;
import com.coderblack.hms.feedback.request.CreateFeedbackRequest;
import com.coderblack.hms.feedback.request.UpdateFeedbackRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    private FeedbackService feedbackService;

    @PostMapping("add-feedback")
    public ResponseEntity<DefaultResponse> addFeedback(
            @RequestBody @Valid CreateFeedbackRequest request,
            Authentication connectedUser
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.feedbackService.addFeedback(request, connectedUser));
    }

    @PutMapping("update-feedback/{feedbackId}")
    public ResponseEntity<DefaultResponse> updateFeedback(
            @RequestBody @Valid UpdateFeedbackRequest request,
            @PathVariable("feedbackId") String feedbackId,
            Authentication connectedUser
    ){
        return ResponseEntity.status(HttpStatus.OK).body(this.feedbackService.updateFeedback(request, connectedUser, feedbackId));
    }

    @GetMapping("{feedbackId}")
    public ResponseEntity<BaseResponse<FeedbackResponse>> getFeedback(
            @PathVariable("feedbackId") String feedbackId
    ){
        return ResponseEntity.ok(this.feedbackService.getFeedback(feedbackId));
    }

    @GetMapping("")
    public ResponseEntity<PageResponse<FeedbackResponse>> getGuestReservations(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) SortDirection sortDirection,
            Authentication connectedUser
    ) {
        int zeroPage = page - 1;
        return ResponseEntity.ok(feedbackService.getFeedbacks(
                search, zeroPage, limit, sortField, sortDirection, connectedUser
        ));
    }
}
