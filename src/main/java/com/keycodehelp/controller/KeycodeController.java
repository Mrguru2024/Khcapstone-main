package com.keycodehelp.controller;

import com.keycodehelp.entities.Keycode;
import com.keycodehelp.entities.KeycodeRequest;
import com.keycodehelp.entities.User;
import com.keycodehelp.services.KeycodeService;
import com.keycodehelp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController  // Ensures this is a REST controller
@RequestMapping("/api/keycode")
public class KeycodeController {

    @Autowired
    private KeycodeService keycodeService;

    @Autowired
    private UserService userService;

    // Endpoint to convert VIN to Keycode
    @PostMapping("/convert")
    public Keycode convertVinToKeycode(@RequestParam String vin, Principal principal) {
        // Get the currently authenticated user
        User user = userService.findByUsername(principal.getName());

        // Call service to convert VIN to keycode
        return keycodeService.convertVinToKeycode(vin, user);
    }

    // Endpoint to retrieve the keycode request history for the current user
    @GetMapping("/history")
    public List<KeycodeRequest> getKeycodeHistory(Principal principal) {
        // Get the currently authenticated user
        User user = userService.findByUsername(principal.getName());

        // Call service to get keycode request history
        return keycodeService.getKeycodeHistory(user);
    }
}
