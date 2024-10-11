package com.coderblack.hms.user;


import com.coderblack.hms.staff.Staff;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private String avatar;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(unique = true, nullable = false)
    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Staff staff;
    private boolean isAccountActivate;
    private boolean accountLocked=false;
    @Column(nullable = true)
    private String token;

    @Column(nullable = true)
    private LocalDateTime tokenExpireDate;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public User findByIsAccountActivate(String email) {
        return User.builder().build();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }
}
