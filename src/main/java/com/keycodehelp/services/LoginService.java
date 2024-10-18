package com.keycodehelp.services;

import com.keycodehelp.entities.User;
import com.keycodehelp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    // Example method to get User details by username
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user from the repository
        Optional<User> userOptional = userRepository.findByUsername(username);

        // Throw an exception if the user is not found
        return userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    // Example method to validate user credentials
    public boolean validateUserCredentials(String username, String password) {
        // Load the user by username
        User user = loadUserByUsername(username);  // Use the method above and extract the user

        // Now safely access user details like password
        return user.getPassword().equals(password);  // Compare stored password with provided password
    }
}
