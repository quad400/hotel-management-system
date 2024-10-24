package com.coderblack.hms.feedback;


import com.coderblack.hms.feedback.request.CreateFeedbackRequest;
import com.coderblack.hms.feedback.request.UpdateFeedbackRequest;
import com.coderblack.hms.user.User;
import com.coderblack.hms.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FeedbackMapper {

    @Mapping(target = "id", ignore = true)
    Feedback createMapper(CreateFeedbackRequest request, User user);

    Feedback updateMapper(UpdateFeedbackRequest request);

    @Mapping(source = "user", target = "user")
    FeedbackResponse toResponse(Feedback feedback);
    UserResponse toUserResponse(User user);
}
