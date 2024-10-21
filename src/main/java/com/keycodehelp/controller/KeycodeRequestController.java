package com.keycodehelp.controller;

import com.keycodehelp.entities.Keycode;
import com.keycodehelp.entities.KeycodeRequest;
import com.keycodehelp.entities.User;
import com.keycodehelp.services.KeycodeService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/keycode") // does not interact with Thymeleaf
public class KeycodeRequestController {

    private final KeycodeService keycodeService;

    public KeycodeRequestController(KeycodeService keycodeService) {
        this.keycodeService = keycodeService;
    }

    // Display the keycode request form
    @GetMapping("/keycode_request")
    public String showKeycodeRequestForm() {
        return "keycode_request";  // Renders the form for VIN submission
    }

    // Handle keycode request submission
    @PostMapping("/keycode_request")
    public String submitKeycodeRequest(@RequestParam("vin") String vin, Model model) {
        try {
            // Get the current authenticated user
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            // Create the keycode request and generate the keycode
            KeycodeRequest keycodeRequest = keycodeService.createKeycodeRequest(vin, currentUser);
            Keycode keycode = keycodeService.convertVinToKeycode(vin, currentUser);

            // Add keycode information to the model to display on the result page
            model.addAttribute("vin", keycode.getVin());
            model.addAttribute("keycode", keycode.getKeycode());

            return "redirect:/user-dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to generate keycode. Please try again.");
            return "error";  // Return to form on error
        }
    }
}
