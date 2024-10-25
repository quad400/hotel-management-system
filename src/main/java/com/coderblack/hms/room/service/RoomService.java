package com.coderblack.hms.room.service;

import com.coderblack.hms.common.response.BaseResponse;
import com.coderblack.hms.common.response.DefaultResponse;
import com.coderblack.hms.common.response.PageResponse;
import com.coderblack.hms.exception.ConflictException;
import com.coderblack.hms.exception.NotFoundException;
import com.coderblack.hms.room.RoomResponse;
import com.coderblack.hms.room.entity.Room;
import com.coderblack.hms.room.enums.RoomStatus;
import com.coderblack.hms.room.enums.RoomType;
import com.coderblack.hms.room.mapper.RoomMapper;
import com.coderblack.hms.room.repo.RoomRepository;
import com.coderblack.hms.room.request.CreateRoomRequest;
import com.coderblack.hms.room.request.UpdateRoomRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;


    public DefaultResponse createRoom(@Valid CreateRoomRequest request) {
        roomRepository.findByRoomNumber(request.roomNumber()).ifPresent(existed -> {
            throw new ConflictException(String.format("Room with No: %s already exist", request.roomNumber()));
        });
        Room room = roomMapper.toRoom(request);
        roomRepository.save(room);

        return new DefaultResponse("Room Added to Room list successfully", room.getId());
    }

    public DefaultResponse updateRoom(@Valid UpdateRoomRequest request, String roomId) {
        roomRepository.findByRoomNumber(request.roomNumber()).ifPresent(existed -> {
            throw new ConflictException(String.format("Room with No: %s already exist", request.roomNumber()));
        });

        Room existedRoom = roomRepository.findById(roomId).orElseThrow(() ->
                new NotFoundException(String.format("Room with ID: %s not found", roomId)));

        Room room = roomMapper.toRoomUpdate(request, existedRoom);
        roomRepository.save(room);
        return new DefaultResponse("Room Updated successfully", room.getId());
    }

    public PageResponse<RoomResponse> getRooms(String search, Pageable pageable) {
        Page<Room> rooms;

        if (Objects.isNull(search) || search.isEmpty()) {
            rooms = roomRepository.findAll(pageable);
        } else {
            RoomType roomType = null;
            RoomStatus roomStatus = null;
            BigDecimal price = null;

            String[] searchTerms = search.split("\\s+");
            for (String term : searchTerms) {
                try {
                    price = new BigDecimal(term);
                } catch (NumberFormatException e) {
                    // Not a price, continue
                }

                try {
                    roomType = RoomType.valueOf(term.toUpperCase());
                } catch (IllegalArgumentException e) {
                    // Not a valid RoomType
                }

                // Try to convert to RoomStatus
                try {
                    roomStatus = RoomStatus.valueOf(term.toUpperCase());
                } catch (IllegalArgumentException e) {
                    // Not a valid RoomStatus
                }
            }

            rooms = roomRepository.findByRoomNumberOrRoomTypeOrRoomStatusOrPrice(search, roomType, roomStatus, price, pageable);
        }

        List<RoomResponse> roomResponses = rooms.stream()
                .map(roomMapper::toRoomResponse)
                .toList();
        return new PageResponse<>("Rooms Fetched Successfully", rooms.getNumber() + 1,
                rooms.getTotalElements(),
                rooms.getSize(),
                rooms.hasPrevious(),
                rooms.hasNext(),
                roomResponses);
    }

    public BaseResponse<RoomResponse> getRoom(String roomId) {
        RoomResponse room = roomRepository.findById(roomId).map(roomMapper::toRoomResponse).orElseThrow(() -> new NotFoundException(String.format("Room with ID: %s not found", roomId)));

        return new BaseResponse<RoomResponse>("Room Fetched Successfully", room);
    }

    public DefaultResponse deleteRoom(String roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException(String.format("Room with ID: %s not found", roomId)));
        roomRepository.delete(room);
        return new DefaultResponse("Room Deleted successfully", roomId);
    }
}
