package com.keycodehelp.services;

import com.keycodehelp.entities.User;
import com.keycodehelp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> loginUser(String username, String password) {
        // Use Optional<User> and return it directly, no need to convert to Optional<Object>
        Optional<User> userOptional = userRepository.findByUsername(username);

        // Check if the user exists and if the password matches
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Make sure to compare password securely (in practice, use a hashed password comparison)
            if (user.getPassword().equals(password)) {
                return Optional.of(user);  // Return Optional<User>
            }
        }

        return Optional.empty(); // Return empty if login fails
    }
}
