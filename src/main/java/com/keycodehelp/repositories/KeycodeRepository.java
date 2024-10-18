package com.keycodehelp.repositories;

import com.keycodehelp.entities.Keycode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeycodeRepository extends JpaRepository<Keycode, Long> {
    List<Keycode> findByUserId(Long userId);
}
