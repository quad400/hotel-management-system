package com.coderblack.hms.staff;

import com.coderblack.hms.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StaffRepository extends JpaRepository<Staff, String> {

    Optional<Staff> findByUser(User user);

    Page<Staff> findByStaffIdContainingIgnoreCaseOrPositionContainingIgnoreCaseOrSalaryContainingIgnoreCase(String search, String search1, String search2, Pageable pageable);
}
