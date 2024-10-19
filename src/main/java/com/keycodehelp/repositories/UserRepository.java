package com.keycodehelp.repositories;

import com.keycodehelp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Check if a username exists
    boolean existsByUsername(String username);

    // Check if an email exists
    boolean existsByEmail(String email);

    // Find a user by username
    Optional<User> findByUsername(String username);  // Corrected return type to Optional<User>

    Optional<User> findByEmail(String email);
}
