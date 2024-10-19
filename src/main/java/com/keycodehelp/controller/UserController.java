package com.keycodehelp.controller;

import com.keycodehelp.entities.User;
import com.keycodehelp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint to create a new user and assign a role
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user, @RequestParam String roleName) {
        User savedUser = userService.saveUser(user, roleName);
        return ResponseEntity.ok(savedUser);
    }

    // Other endpoints
    // Delete user by username or email
    @PostMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam("username") String username, @RequestParam("email") Optional<String> email) {
        try {
            boolean deleted = userService.deleteUserByUsernameOrEmail(username, email.orElse(null));
            if (deleted) {
                return ResponseEntity.ok("User deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the user.");
        }
    }

}
