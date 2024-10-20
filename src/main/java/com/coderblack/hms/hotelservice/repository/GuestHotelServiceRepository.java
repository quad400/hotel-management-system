package com.coderblack.hms.hotelservice.repository;

import com.coderblack.hms.hotelservice.entity.GuestHotelService;
import com.coderblack.hms.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuestHotelServiceRepository extends JpaRepository<GuestHotelService, String> {
    Optional<GuestHotelService> findByIdAndUser(String orderServiceId, User user);
}
