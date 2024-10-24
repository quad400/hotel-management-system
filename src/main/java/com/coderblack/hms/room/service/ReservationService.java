package com.coderblack.hms.room.service;

import com.coderblack.hms.common.response.BaseResponse;
import com.coderblack.hms.common.response.DefaultResponse;
import com.coderblack.hms.common.response.PageResponse;
import com.coderblack.hms.common.response.SearchResponse;
import com.coderblack.hms.common.utils.GenericSearch;
import com.coderblack.hms.common.utils.SortDirection;
import com.coderblack.hms.exception.NotFoundException;
import com.coderblack.hms.room.ReservationResponse;
import com.coderblack.hms.room.entity.Reservation;
import com.coderblack.hms.room.entity.Room;
import com.coderblack.hms.room.enums.ReservationStatus;
import com.coderblack.hms.room.mapper.ReservationMapper;
import com.coderblack.hms.room.repo.ReservationRepository;
import com.coderblack.hms.room.repo.RoomRepository;
import com.coderblack.hms.room.request.CreateReservationRequest;
import com.coderblack.hms.room.request.UpdateReservationRequest;
import com.coderblack.hms.user.User;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final ReservationMapper reservationMapper;
    private final GenericSearch<Reservation> genericSearch;

    public DefaultResponse create(@Valid CreateReservationRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Room room = roomRepository.findById(request.roomId()).orElseThrow(
                () -> new NotFoundException((
                        String.format("Room with ID: %s not found", request.roomId()))
                ));
        Reservation reservation = reservationMapper.toCreateReservation(request, user, room, ReservationStatus.PENDING);
        reservationRepository.save(reservation);

        return new DefaultResponse(String.format("Room with Suit No: %s successfully reserved", room.getRoomNumber()));
    }

    public boolean hasPermission(String reservationId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new NotFoundException("Reservation not found"));

        return reservation.getUser().getId().equals(user.getId());
    }

    public DefaultResponse updateReservation(String reservationId, @Valid UpdateReservationRequest request, Authentication connectedUser) {
        Reservation existedReservation = reservationRepository.findById(reservationId).orElseThrow(() -> new NotFoundException("Reservation not found"));
        Reservation reservation = reservationMapper.toUpdateReservation(request, existedReservation);
        reservationRepository.save(reservation);
        return new DefaultResponse("Reservations successfully updated", reservation.getId());
    }

    public BaseResponse<ReservationResponse> getReservation(String reservationId) {
        ReservationResponse reservation = reservationRepository.findById(reservationId)
                .map(reservationMapper::toReservationResponse)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));
        return new BaseResponse<ReservationResponse>(
                "Reservation fetched successfully",
                reservation
        );
    }

    public PageResponse<ReservationResponse> getReservations(
            String searchText,
            int page, int limit,
            String sortField,
            SortDirection sortDirection
    ) {
        String[] reservationFields = {"status", "checkInDate", "checkOutDate"}; // Only include fields from Reservation
        String[] userJoinFields = {"firstName", "lastName", "email"}; // Fields from User entity
        String[] roomJoinFields = {"roomNumber", "description", "roomType", "roomStatus", "capacity", "price"}; // Fields from Room entity
        String[] joinFields = {"user", "room"};

        SearchResponse<Reservation> reservations;
        reservations = genericSearch.search(
                searchText, Reservation.class,
                page, limit,
                reservationFields, joinFields,
                userJoinFields, sortField, sortDirection
        );

        if (reservations.results().isEmpty()) {
            reservations = genericSearch.search(
                    searchText, Reservation.class,
                     page, limit,
                    reservationFields, joinFields,
                    roomJoinFields, sortField, sortDirection
            );
        }

        List<ReservationResponse> response = reservations.results()
                .stream().map(reservationMapper::toReservationResponse).toList();
        int currentPage = page + 1;
        long totalCount = reservations.totalCount();
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

    public PageResponse<ReservationResponse> getGuestReservations(String search, int page, int limit, String sortField, SortDirection sortDirection, Authentication connectedUser) {
        String[] reservationFields = {"status", "checkInDate", "checkOutDate"}; // Only include fields from Reservation
        String[] userJoinFields = {"firstName", "lastName", "email"}; // Fields from User entity
        String[] roomJoinFields = {"roomNumber", "description", "roomType", "roomStatus", "capacity", "price"}; // Fields from Room entity
        String[] joinFields = {"user", "room"};

        String userId = ((User) connectedUser.getPrincipal()).getId();

        SearchResponse<Reservation> reservations;
        reservations = genericSearch.search(
                search, Reservation.class,
                 page, limit,
                reservationFields, joinFields,
                userJoinFields, sortField, sortDirection,userId
        );


        if (reservations.results().isEmpty()) {
            reservations = genericSearch.search(
                    search, Reservation.class,
                    page, limit,
                    reservationFields, joinFields,
                    roomJoinFields, sortField, sortDirection,
                    userId
            );
        }
        List<ReservationResponse> response = reservations.results()
                .stream().map(reservationMapper::toReservationResponse).toList();
        int currentPage = page + 1;
        long totalCount = reservations.totalCount();
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

    public DefaultResponse checkInReservation(String reservationId, Authentication connectedUser) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));
        reservation.setCheckInDate(LocalDateTime.now());
        this.reservationRepository.save(reservation);

        return new DefaultResponse("Successfully checked In", reservationId);
    }

    public DefaultResponse checkOutReservation(String reservationId, Authentication connectedUser) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));
        reservation.setCheckOutDate(LocalDateTime.now());
        this.reservationRepository.save(reservation);

        return new DefaultResponse("Successfully checked Out", reservationId);
    }
}