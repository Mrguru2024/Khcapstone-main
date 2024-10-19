package com.keycodehelp.services;

import com.keycodehelp.entities.Role;
import com.keycodehelp.entities.User;
import com.keycodehelp.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    // Constructor for UserService with dependency injection
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    // Find a user by username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Check if username or email already exists
    public boolean existsByUsernameOrEmail(String username, String email) {
        return userRepository.existsByUsername(username) || userRepository.existsByEmail(email);
    }

    // Save a new user and assign roles
    public User saveUser(User user, String roleName) {
        // Find the role by name
        Role role = roleService.getRoleByName(roleName);
        // Hash the user's password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (role == null) {
            throw new IllegalArgumentException("Role not found: " + roleName);
        }

        // Add the role to the user's roles
        user.getRoles().add(role);

        // Encode the user's password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save and return the user entity
        return userRepository.save(user);
    }

    // Save a user without passing a role name (roles should already be assigned)
    public User saveUser(User user) {
        // Encode the password if it's not already encoded
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Method to delete a user by username or email
    public boolean deleteUserByUsernameOrEmail(String username, String email) {
        Optional<User> userOpt;
        if (email != null) {
            userOpt = userRepository.findByEmail(email);
        } else {
            userOpt = userRepository.findByUsername(username);
        }

        if (userOpt.isPresent()) {
            userRepository.delete(userOpt.get());
            return true;
        }
        return false;
    }

    // Find role by name using RoleService
    public Optional<Role> findRoleByName(String roleName) {
        return Optional.ofNullable(roleService.getRoleByName(roleName));
    }
}
