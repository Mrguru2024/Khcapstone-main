package com.keycodehelp.repositories;

import com.keycodehelp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Query to find User by username
    Optional<User> findByUsername(String username);
}
