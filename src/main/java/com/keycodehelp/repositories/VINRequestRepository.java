package com.keycodehelp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.keycodehelp.entities.VINRequest;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VINRequestRepository extends JpaRepository<VINRequest, Long> {
    // Additional custom queries can go here
    List<VINRequest> findByVin(String vin);

    List<VINRequest> findByRequestTimeAfter(LocalDateTime requestTime);

    List<VINRequest> findByUserId(Long userId);

}
