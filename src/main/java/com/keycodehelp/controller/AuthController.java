package com.keycodehelp.controller;

import com.keycodehelp.entities.User;
import com.keycodehelp.exception.UserAlreadyExistsException;
import com.keycodehelp.services.UserService;
<<<<<<< HEAD
import lombok.Getter;
=======
>>>>>>> 6077084 (Added tailwind proper config and classes for basic front end use, Added Exception, Dev-prop, Prop-prop, input.css, style.css, added missing controllers, fragments folder, customized header, footer and navbar (ready to use as a component. Fixed looping issues in the path settings.))
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.*;
=======
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
>>>>>>> 6077084 (Added tailwind proper config and classes for basic front end use, Added Exception, Dev-prop, Prop-prop, input.css, style.css, added missing controllers, fragments folder, customized header, footer and navbar (ready to use as a component. Fixed looping issues in the path settings.))

@Controller
public class AuthController {

    private final AuthenticationManager authenticationManager;
    @Getter
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }
<<<<<<< HEAD

    // Display login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";  // Render the login.html Thymeleaf template
    }

    // Handle login submission
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Redirect to dashboard based on user role
            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/admin";  // Admin dashboard
            } else {
                return "redirect:/user-dashboard";  // User dashboard
            }
        } catch (Exception e) {
            model.addAttribute("error", "Invalid username or password");
            return "login";  // Send back to login page in case of error
        }
    }

    // Display registration page
    @GetMapping("/auth/register" + "/register")
    public String showRegistrationPage() {
        return "register";  // Render the register.html Thymeleaf template
    }

=======
>>>>>>> 6077084 (Added tailwind proper config and classes for basic front end use, Added Exception, Dev-prop, Prop-prop, input.css, style.css, added missing controllers, fragments folder, customized header, footer and navbar (ready to use as a component. Fixed looping issues in the path settings.))
    // Handle user registration
    @PostMapping("/auth/register")
    public String handleRegistration(@ModelAttribute User user, Model model) {
        try {
            // Store the raw password before encoding
            String rawPassword = user.getPassword();

            // Save the registered user to the database using UserService
            userService.saveUser(user, "ROLE_USER");  // Assign ROLE_USER to the new user

            // Authenticate the user with the raw password
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), rawPassword);

            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(authToken);

            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Redirect the user to the dashboard after registration and login
            return "redirect:/user-dashboard";
        } catch (UserAlreadyExistsException e) {
            // Handle duplicate user case
            model.addAttribute("error", e.getMessage());
            return "register";  // Send back to the register page with an error message
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging purposes
            model.addAttribute("error", "There was an error during registration.");
            return "register";  // Send back to the register page in case of error
        }
    }
<<<<<<< HEAD

    // Admin dashboard
    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("pageTitle", "Admin Dashboard");
        return "admin";  // Render the admin.html Thymeleaf template
    }

    @GetMapping("/admin/tasks")
    public String adminTasks(Model model) {
        model.addAttribute("pageTitle", "Admin Tasks");
        return "admin_tasks";  // Render the admin_tasks.html Thymeleaf template
    }

    // User dashboard
=======
>>>>>>> 6077084 (Added tailwind proper config and classes for basic front end use, Added Exception, Dev-prop, Prop-prop, input.css, style.css, added missing controllers, fragments folder, customized header, footer and navbar (ready to use as a component. Fixed looping issues in the path settings.))
    @GetMapping("/user-dashboard")
    public String userDashboard(Model model) {
        // Add any required attributes to the model if needed
        model.addAttribute("pageTitle", "User Dashboard");
        return "user-dashboard";  // Render user-dashboard.html Thymeleaf template
    }

}
