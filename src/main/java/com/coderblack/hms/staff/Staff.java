package com.coderblack.hms.staff;

import com.coderblack.hms.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String address;
    private LocalDate dateOfBirth;
    private String staffId;
    @Enumerated(EnumType.STRING)
    private Position position;
    private BigDecimal  salary;
    @CreatedDate
    @Column(updatable = true, nullable = false)
    private LocalDate dateOfJoining;
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
}
