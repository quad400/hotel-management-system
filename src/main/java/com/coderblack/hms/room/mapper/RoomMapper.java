package com.coderblack.hms.room.mapper;

import com.coderblack.hms.room.RoomResponse;
import com.coderblack.hms.room.entity.Room;
import com.coderblack.hms.room.request.CreateRoomRequest;
import com.coderblack.hms.room.request.UpdateRoomRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    @Mapping(target = "id", ignore = true)
    Room toRoom(CreateRoomRequest request);

    RoomResponse toRoomResponse(Room room);

    Room toRoomUpdate(UpdateRoomRequest request, @MappingTarget Room room);
}
