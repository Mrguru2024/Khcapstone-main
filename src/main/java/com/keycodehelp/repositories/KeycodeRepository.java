package com.keycodehelp.repositories;

import com.keycodehelp.entities.Keycode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface KeycodeRepository extends JpaRepository<Keycode, Long> {

    // Find keycodes by user ID
    @Query("SELECT k FROM Keycode k WHERE k.user.id = :userId")
    List<Keycode> findByUserId(@Param("userId") Long userId);

    // Find keycode by VIN
    Optional<Keycode> findByVin(String vin);

    // Find keycodes created after a certain date
    List<Keycode> findByCreatedAtAfter(LocalDateTime createdAt);
}
