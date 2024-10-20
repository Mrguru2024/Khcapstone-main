package com.keycodehelp.controller;

import com.keycodehelp.dto.SignupRequest;
import com.keycodehelp.entities.Role;
import com.keycodehelp.entities.User;
import com.keycodehelp.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    @Autowired
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    // Serve the signup page with a GET request
    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        model.addAttribute("signupRequest", new SignupRequest());
        return "register";  // Matches register.html in templates folder
    }

    @PostMapping("/signup")
    public String registerUser(@Valid @ModelAttribute SignupRequest signupRequest, Model model, HttpServletRequest request) {
        // Check if the username or email already exists
        if (userService.existsByUsernameOrEmail(signupRequest.getUsername(), signupRequest.getEmail())) {
            model.addAttribute("error", "Username or email already exists.");
            return "register"; // Send back to register page with error
        }

        // Create a new user
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());  // Password encoding will happen in UserService

        // Find the "ROLE_USER" role and assign it to the new user
        Optional<Role> userRole = userService.findRoleByName("ROLE_USER");
        if (userRole.isEmpty()) {
            model.addAttribute("error", "User role not found.");
            return "register"; // Send back to register page if a role not found
        }

        // Add the role to the user
        user.getRoles().add(userRole.get());

        // Save the user (with encoded password in UserService)
        try {
            userService.saveUser(user);
        } catch (Exception e) {
            model.addAttribute("error", "Error occurred during registration.");
            return "register";  // Stay on the registration page with an error
        }

        // Automatically log in the user after registration
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), signupRequest.getPassword());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Set the authentication in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // Redirect to the dashboard or a welcome page after successful signup
        return "redirect:/user-dashboard";  // Correct redirect
    }
}
