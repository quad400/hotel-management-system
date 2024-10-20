package com.coderblack.hms.room.mapper;


import com.coderblack.hms.room.ReservationResponse;
import com.coderblack.hms.room.RoomResponse;
import com.coderblack.hms.room.entity.Reservation;
import com.coderblack.hms.room.entity.Room;
import com.coderblack.hms.room.enums.ReservationStatus;
import com.coderblack.hms.room.request.CreateReservationRequest;
import com.coderblack.hms.room.request.UpdateReservationRequest;
import com.coderblack.hms.user.User;
import com.coderblack.hms.user.UserResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    @Mapping(target = "id", ignore = true)
    Reservation toCreateReservation(CreateReservationRequest request, User user, Room room, ReservationStatus status);

    Reservation toUpdateReservation(UpdateReservationRequest request, @MappingTarget  Reservation reservation);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "room", target = "room")
    ReservationResponse toReservationResponse(Reservation reservation);
    UserResponse toUserResponse(User user);
    RoomResponse toRoomResponse(Room room);
}
