package com.keycodehelp.services;

import com.keycodehelp.entities.Role;
import com.keycodehelp.entities.User;
import com.keycodehelp.exception.UserAlreadyExistsException;
import com.keycodehelp.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Optional;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

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

    // Save a new user and assign roles, throw exception if user already exists
    public User saveUser(User user, String roleName) {
        // Check if the user already exists by username or email
        if (existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            logger.error("User with the given username or email already exists: {}", user.getUsername());
            throw new UserAlreadyExistsException("User with the given username or email already exists.");
        }

        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Find and assign the role to the user
        Role role = roleService.getRoleByName(roleName);
        if (role == null) {
            throw new IllegalArgumentException("Role not found: " + roleName);
        }
        user.getRoles().add(role);

        // Log user registration and save the user
        logger.info("Saving new user: {}", user.getUsername());
        return userRepository.save(user);
    }

    // Register a new user with validation on username, email, and password
    public User registerNewUser(
            @NotBlank(message = "Username is required")
            @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters") String username,
            @NotBlank(message = "Email is required")
            @Email(message = "Email should be valid") String email,
            @NotBlank(message = "Password is required")
            @Size(min = 6, message = "Password must be at least 6 characters long") String password) {

        // Check if the user already exists by username or email
        if (existsByUsernameOrEmail(username, email)) {
            throw new UserAlreadyExistsException("User with the given username or email already exists.");
        }

        // Create a new user object
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Encode the password before saving

        // Assign the "ROLE_USER" role to the new user
        Role role = roleService.getRoleByName("ROLE_USER");
        if (role == null) {
            throw new IllegalArgumentException("Role not found: ROLE_USER");
        }
        user.getRoles().add(role);

        // Log user registration and save the user
        logger.info("Saving new user: {}", username);
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
            logger.info("User deleted: {}", userOpt.get().getUsername());
            return true;
        }

        return false;
    }

    // Find a role by name using RoleService
    public Optional<Role> findRoleByName(String roleName) {
        return Optional.ofNullable(roleService.getRoleByName(roleName));
    }
}
