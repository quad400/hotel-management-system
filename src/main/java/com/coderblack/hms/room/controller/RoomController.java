package com.coderblack.hms.room.controller;

import com.coderblack.hms.common.response.BaseResponse;
import com.coderblack.hms.common.response.DefaultResponse;
import com.coderblack.hms.common.response.PageResponse;
import com.coderblack.hms.room.RoomResponse;
import com.coderblack.hms.room.request.CreateRoomRequest;
import com.coderblack.hms.room.request.UpdateRoomRequest;
import com.coderblack.hms.room.service.RoomService;
import com.coderblack.hms.staff.StaffResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PreAuthorize("hasAuthority('staff:write')")
    @PostMapping("/create-room")
    public ResponseEntity<DefaultResponse> createRoom(
            @RequestBody @Valid CreateRoomRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(request));
    }

    @PreAuthorize("hasAuthority('staff:write')")
    @PutMapping("/update-room/{roomId}")
    public ResponseEntity<DefaultResponse> updateRoom(
            @RequestBody @Valid UpdateRoomRequest request,
            @PathVariable("roomId") String roomId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(roomService.updateRoom(request, roomId));
    }


    @GetMapping("/get-room/{roomId}")
    public ResponseEntity<BaseResponse<RoomResponse>> getRoom(
            @PathVariable("roomId") String roomId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.getRoom(roomId));
    }

    @GetMapping("/get-rooms")
    public ResponseEntity<PageResponse<RoomResponse>> getStaffs(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "roomNumber") String sort
    ) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(sort));
        return ResponseEntity.status(HttpStatus.OK).body(roomService.getRooms(search, pageable));
    }

    @DeleteMapping("/delete-room/{roomId}")
    public ResponseEntity<DefaultResponse> deleteRoom(
            @PathVariable("roomId") String roomId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.deleteRoom(roomId));
    }

}
