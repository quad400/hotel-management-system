package com.coderblack.hms.hotelservice;

import com.coderblack.hms.hotelservice.entity.OrderService;
import com.coderblack.hms.hotelservice.entity.HotelService;
import com.coderblack.hms.hotelservice.request.AddServiceRequest;
import com.coderblack.hms.hotelservice.request.OrderServiceRequest;
import com.coderblack.hms.hotelservice.request.UpdateServiceRequest;
import com.coderblack.hms.hotelservice.response.GuestHotelServiceResponse;
import com.coderblack.hms.user.User;
import com.coderblack.hms.user.UserResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface HotelServiceMapper {

    HotelServiceMapper INSTANCE = Mappers.getMapper(HotelServiceMapper.class);

    @Mapping(target = "id", ignore = true)
    HotelService toAddService(AddServiceRequest request);

    HotelService toUpdateService(UpdateServiceRequest request, @MappingTarget HotelService service);

    HotelServiceResponse toResponse(HotelService service);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "hotelService", target = "hotelService")
    GuestHotelServiceResponse toGuestOrderResponse(OrderService guestHotelService);
    UserResponse toUserResponse(User user);

}
