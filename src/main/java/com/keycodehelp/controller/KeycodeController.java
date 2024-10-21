package com.keycodehelp.controller;

import com.keycodehelp.entities.Keycode;
import com.keycodehelp.entities.KeycodeRequest;
import com.keycodehelp.entities.User;
import com.keycodehelp.services.KeycodeService;
import com.keycodehelp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  // Correct import for Model
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/keycode")
public class KeycodeController {

    private final KeycodeService keycodeService;
    private final UserService userService;

    @Autowired
    public KeycodeController(KeycodeService keycodeService, UserService userService) {
        this.keycodeService = keycodeService;
        this.userService = userService;
    }

    // Endpoint to show the keycode by VIN
    @GetMapping("/keycode/show")
    public String showKeycode(@RequestParam("vin") String vin, Model model, Principal principal) {
        // Fetch the keycode by VIN
        Keycode keycode = keycodeService.getKeycodeByVin(vin);

        // Ensure the keycode exists, otherwise handle the case
        if (keycode == null) {
            model.addAttribute("errorMessage", "Keycode not found for VIN: " + vin);
            return "error"; // Return error page if keycode is not found
        }

        // Pass the keycode to the view with a distinct name
        model.addAttribute("generatedKeycode", keycode.getKeycode());

        return "convert"; // Return the page where you display the keycode
    }

    // Endpoint to convert VIN to Keycode
    @PostMapping("/convert")
    public ResponseEntity<Keycode> convertVinToKeycode(@RequestParam String vin, Principal principal) {
        // Get the currently authenticated user
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Call service to convert VIN to keycode
        Keycode keycode = keycodeService.convertVinToKeycode(vin, user);

        // If the keycode could not be generated, return a 404 response
        if (keycode == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(keycode);
    }

    // Endpoint to retrieve the keycode request history for the current user
    @GetMapping("/history")
    public ResponseEntity<List<KeycodeRequest>> getKeycodeHistory(Principal principal) {
        // Get the currently authenticated user
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Call service to get keycode request history
        List<KeycodeRequest> history = keycodeService.getKeycodeHistory(user);

        if (history.isEmpty()) {
            return ResponseEntity.noContent().build();  // Return 204 No Content if history is empty
        }

        return ResponseEntity.ok(history);
    }
}
