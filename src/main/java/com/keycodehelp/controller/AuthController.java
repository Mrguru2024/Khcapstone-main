package com.keycodehelp.controller;

import com.keycodehelp.entities.User;
import com.keycodehelp.exception.UserAlreadyExistsException;
import com.keycodehelp.services.UserService;

import lombok.Getter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AuthenticationManager authenticationManager;
    @Getter
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    // Display login page for users (ROLE_USER)
    @GetMapping("/login")
    public String showUserLoginPage(@RequestParam(value = "error", required = false) String error,
                                    @RequestParam(value = "sessionExpired", required = false) String sessionExpired,
                                    Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        if (sessionExpired != null) {
            model.addAttribute("sessionExpired", "Your session has expired. Please log in again.");
        }
        return "login";  // Renders the login.html page for regular users
    }

    // Display login page for admins (ROLE_ADMIN)
    @GetMapping("/admin/login")
    public String showAdminLoginPage(@RequestParam(value = "error", required = false) String error,
                                     Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        return "authlogin";  // Renders the authlogin.html page for admins
    }

    // Handle login submission for users and admins
    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model) {
        try {
            // Authenticate the user or admin
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Redirect based on user role
            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/admin";  // Redirect to admin dashboard
            } else {
                return "redirect:/user-dashboard";  // Redirect to user dashboard
            }
        } catch (Exception e) {
            // Display error message for login failure
            model.addAttribute("error", "Invalid username or password");
            return username.equalsIgnoreCase("admin") ? "authlogin" : "login";  // Return respective login page with error
        }
    }

    // Display registration page
    @GetMapping("/auth/register")
    public String showRegistrationPage() {
        return "register";  // Renders the register.html page for new users
    }

    // Handle user registration
    @PostMapping("/auth/register")
    public String handleRegistration(@ModelAttribute User user, Model model) {
        try {
            // Save the registered user with "ROLE_USER" using UserService
            userService.saveUser(user, "ROLE_USER");

            // Automatically authenticate the user upon registration
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Redirect to user dashboard after registration and login
            return "redirect:/user-dashboard";
        } catch (UserAlreadyExistsException e) {
            // Handle duplicate user case
            model.addAttribute("error", e.getMessage());
            return "register";  // Return register page with an error message
        } catch (Exception e) {
            model.addAttribute("error", "There was an error during registration.");
            return "register";  // Return register page with error
        }
    }

    // Admin dashboard
    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("pageTitle", "Admin Dashboard");
        return "admin";  // Renders the admin.html page
    }

    // Admin tasks page
    @GetMapping("/admin/tasks")
    public String adminTasks(Model model) {
        model.addAttribute("pageTitle", "Admin Tasks");
        return "admin_tasks";  // Renders the admin_tasks.html page
    }
    // Handle user update
    @PostMapping("/edit-user/{id}")
    public <AppUser> String updateUser(@PathVariable Long id, @ModelAttribute("appUser") AppUser updatedUser, Model model) {
        model.addAttribute("pageTitle", "Update User");
        return "redirect:/admin";
    }

    // Handle user deletion
    @GetMapping("/edit-user{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
       model.addAttribute("pageTitle", "Delete User");
        return "redirect:/admin/adminDashboard";
    }
    // User dashboard
    @GetMapping("/user-dashboard")
    public String userDashboard(Model model) {
        model.addAttribute("pageTitle", "User Dashboard");
        return "user-dashboard";  // Renders the user-dashboard.html page
    }
}
