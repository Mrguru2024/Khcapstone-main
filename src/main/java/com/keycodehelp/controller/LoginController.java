package com.keycodehelp.controller;

import com.keycodehelp.security.JwtUtil;
import com.keycodehelp.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // Endpoint to handle login
    @PostMapping
    public String login(@RequestParam String username, @RequestParam String password) {
        try {
            // Authenticate the user with username and password
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            // Load user details
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // Generate JWT token
            String jwtToken = jwtUtil.generateToken(userDetails.getUsername());

            // Return the token in the response
            return jwtToken;
        } catch (AuthenticationException e) {
            // In case of invalid credentials
            return "Invalid username or password!";
        }
    }
}
