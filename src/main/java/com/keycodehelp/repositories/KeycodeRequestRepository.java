package com.keycodehelp.repositories;

import com.keycodehelp.entities.KeycodeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeycodeRequestRepository extends JpaRepository<KeycodeRequest, Long> {
    List<KeycodeRequest> findByUserId(Long userId);
}
