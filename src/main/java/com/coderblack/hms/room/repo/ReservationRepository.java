package com.coderblack.hms.room.repo;

import com.coderblack.hms.room.entity.Reservation;
import com.coderblack.hms.room.enums.ReservationStatus;
import com.coderblack.hms.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservationRepository extends JpaRepository<Reservation, String> {
    Page<Reservation> findByUserOrStatus(User user, ReservationStatus status, Pageable pageable);

    Page<Reservation> findByUser(User user, Pageable pageable);
}
