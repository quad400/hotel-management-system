package com.coderblack.hms.room.entity;

import com.coderblack.hms.common.contraint.HotelServiceCategory;
import com.coderblack.hms.room.enums.MaintenanceStatus;
import com.coderblack.hms.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity(name = "hotel_services")
public class HotelService {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String description;

    private HotelServiceCategory serviceType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenanceStatus status;
}
