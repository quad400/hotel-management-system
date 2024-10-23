package com.coderblack.hms.room.controller;

import com.coderblack.hms.common.response.BaseResponse;
import com.coderblack.hms.common.response.DefaultResponse;
import com.coderblack.hms.common.response.PageResponse;
import com.coderblack.hms.common.utils.SortDirection;
import com.coderblack.hms.room.ReservationResponse;
import com.coderblack.hms.room.RoomResponse;
import com.coderblack.hms.room.entity.Reservation;
import com.coderblack.hms.room.request.CreateReservationRequest;
import com.coderblack.hms.room.request.UpdateReservationRequest;
import com.coderblack.hms.room.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<DefaultResponse> create(
            @RequestBody @Valid CreateReservationRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.create(request, connectedUser));
    }

    @PreAuthorize("hasRole('STAFF')")
    @PutMapping("/update/{reservationId}")
    public ResponseEntity<DefaultResponse> updateReservation(
            @PathVariable("reservationId") String reservationId,
            @RequestBody @Valid UpdateReservationRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.updateReservation(reservationId, request, connectedUser));
    }

    @PreAuthorize("hasRole('STAFF')")
    @PatchMapping("/check-in/{reservationId}")
    public ResponseEntity<DefaultResponse> checkInReservation(
            @PathVariable("reservationId") String reservationId,
            Authentication connectedUser
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.checkInReservation(reservationId, connectedUser));
    }

    @PreAuthorize("hasRole('STAFF')")
    @PatchMapping("/check-out/{reservationId}")
    public ResponseEntity<DefaultResponse> checkOutReservation(
            @PathVariable("reservationId") String reservationId,
            Authentication connectedUser
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.checkOutReservation(reservationId, connectedUser));
    }

    @GetMapping("/get-reservation/{reservationId}")
    public ResponseEntity<BaseResponse<ReservationResponse>> getReservation(
            @PathVariable("reservationId") String reservationId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getReservation(reservationId));
    }

    @GetMapping("/get-reservations")
    public ResponseEntity<PageResponse<ReservationResponse>> getReservations(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) SortDirection sortDirection
    ) {
        int zeroPage = page - 1;
        return ResponseEntity.ok(reservationService.getReservations(
                search, zeroPage, limit, sortField, sortDirection
        ));
    }

    @GetMapping("/get-guest-reservations")
    public ResponseEntity<PageResponse<ReservationResponse>> getGuestReservations(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) SortDirection sortDirection,
            Authentication connectedUser
    ) {
        int zeroPage = page - 1;
        return ResponseEntity.ok(reservationService.getGuestReservations(
                search, zeroPage, limit, sortField, sortDirection, connectedUser
        ));
    }

}
