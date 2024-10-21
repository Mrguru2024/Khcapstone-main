package com.keycodehelp.repositories;

import com.keycodehelp.entities.KeycodeRequest;
import com.keycodehelp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeycodeRequestRepository extends JpaRepository<KeycodeRequest, Long> {
    KeycodeRequest findByVin(String vin);
    List<KeycodeRequest> findAllByUser(User user);  // Custom method to get requests by user
}
