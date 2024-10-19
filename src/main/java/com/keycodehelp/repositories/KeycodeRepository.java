package com.keycodehelp.repositories;

import com.keycodehelp.entities.Keycode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface KeycodeRepository extends JpaRepository<Keycode, Long> {

    // Find all keycodes by user ID
    List<Keycode> findByUserId(Long userId);

    // Find a keycode by VIN (since VIN is unique)
    Optional<Keycode> findByVin(String vin);

    // Find keycodes created after a certain date
    List<Keycode> findByCreatedAtAfter(LocalDateTime createdAt);
}
