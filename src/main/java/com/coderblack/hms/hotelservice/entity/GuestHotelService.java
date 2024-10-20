package com.coderblack.hms.hotelservice.entity;


import com.coderblack.hms.common.contraint.GuestHotelServiceStatus;
import com.coderblack.hms.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "guest services")
@EntityListeners(AuditingEntityListener.class)
public class GuestHotelService {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private HotelService hotelService;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String details;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GuestHotelServiceStatus status;

    @Column(updatable = false, nullable = false)
    @CreatedDate()
    private LocalDateTime createdAt;
    @Column(insertable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
