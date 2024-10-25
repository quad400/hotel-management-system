package com.coderblack.hms.room.repo;


import com.coderblack.hms.room.entity.Room;
import com.coderblack.hms.room.enums.RoomStatus;
import com.coderblack.hms.room.enums.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, String> {
    Optional<Room> findByRoomNumber(String roomNumber);

    Page<Room> findByRoomNumberOrRoomTypeOrRoomStatusOrPrice(String search, RoomType search1, RoomStatus search2, BigDecimal search3, Pageable pageable);
}
