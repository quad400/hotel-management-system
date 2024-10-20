package com.coderblack.hms.hotelservice;

import com.coderblack.hms.hotelservice.entity.HotelService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelServiceRepository extends JpaRepository<HotelService, String> {
    Optional<HotelService> findByName(String name);
}
