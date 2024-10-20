package com.keycodehelp.controller;

import com.keycodehelp.dto.AuthenticationResponse;
import com.keycodehelp.entities.User;
import com.keycodehelp.services.UserService;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Handle user registration
    @PostMapping("/auth/register")
    public String handleRegistration(@ModelAttribute User user, Model model) {
        try {
            // Encode the user's password before saving it
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Save the registered user to the database using UserService
            userService.saveUser(user, "ROLE_USER");  // Assign ROLE_USER to the new user

            // Automatically log the user in after successful registration
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(authToken);

            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Redirect the user to the dashboard after registration and login
            return "redirect:/user-dashboard";
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging purposes
            model.addAttribute("error", "There was an error during registration.");
            return "register";  // Send back to the register page in case of error (no leading slash)
        }
    }

    // Admin dashboard
    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("pageTitle", "Admin Dashboard");
        return "admin";  // Render the admin.html Thymeleaf template
    }

    // Admin tasks page
    @GetMapping("/admin/tasks")
    public String adminTasks(Model model) {
        model.addAttribute("pageTitle", "Admin Tasks");
        return "admin_tasks";  // Render the admin_tasks.html Thymeleaf template
    }

    // User dashboard
    @GetMapping("/user-dashboard")
    public String userDashboard(Model model) {
        model.addAttribute("pageTitle", "User Dashboard");
        return "user-dashboard";  // Render the user-dashboard.html Thymeleaf template
    }
}
