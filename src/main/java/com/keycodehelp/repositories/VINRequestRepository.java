package com.keycodehelp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.keycodehelp.entities.VINRequest;

@Repository
public interface VINRequestRepository extends JpaRepository<VINRequest, Long> {
    // Additional custom queries can go here
}
