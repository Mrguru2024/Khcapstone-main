//package com.keycodehelp.controller;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class LoginController {
//
//    // Inject AuthenticationManager
//    private final AuthenticationManager authenticationManager;
//
//    public LoginController(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    @GetMapping("/login")
//    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
//                                @RequestParam(value = "sessionExpired", required = false) String sessionExpired,
//                                Model model) {
//        if (error != null) {
//            model.addAttribute("error", "Invalid username or password");
//        }
//        if (sessionExpired != null) {
//            model.addAttribute("sessionExpired", "Your session has expired. Please log in again.");
//        }
//        return "login";  // Returns login.html template for regular users
//    }
//
//    // Handle login submission for regular users (ROLE_USER)
//    @PostMapping("/login")
//    public String loginUser(@RequestParam("username") String username,
//                            @RequestParam("password") String password,
//                            Model model) {
//        try {
//            // Authenticate the user using AuthenticationManager
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password)
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            // Redirect to user dashboard after successful login
//            return "redirect:/user-dashboard";
//        } catch (Exception e) {
//            // Display error message for login failure
//            model.addAttribute("error", "Invalid username or password");
//            return "login";  // Return login page with error
//        }
//    }
//}
