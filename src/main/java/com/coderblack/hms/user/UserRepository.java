package com.coderblack.hms.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndIsAccountActivate(String email, boolean isAccountActivate);

    Optional<User> findByEmailAndTokenAndTokenExpireDateGreaterThan(String email, String token, LocalDateTime tokenExpireDate);

    Page<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String search1, String search2, String search3, Pageable pageable);
}
