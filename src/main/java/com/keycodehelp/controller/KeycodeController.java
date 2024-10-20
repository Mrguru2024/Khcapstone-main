package com.keycodehelp.controller;

import com.keycodehelp.entities.Keycode;
import com.keycodehelp.entities.KeycodeRequest;
import com.keycodehelp.entities.User;
import com.keycodehelp.services.KeycodeService;
import com.keycodehelp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/keycode")
public class KeycodeController {

    private final KeycodeService keycodeService;
    private final UserService userService;

    public KeycodeController(KeycodeService keycodeService, UserService userService) {
        this.keycodeService = keycodeService;
        this.userService = userService;
    }

    @PostMapping("/keycode/convert")
    public ResponseEntity<Keycode> convertVinToKeycode(@RequestParam String vin, Principal principal) {
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Keycode keycode = keycodeService.convertVinToKeycode(vin, user);
        return ResponseEntity.ok(keycode);
    }

    @GetMapping("/keycode/history")
    public ResponseEntity<List<KeycodeRequest>> getKeycodeHistory(Principal principal) {
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<KeycodeRequest> history = keycodeService.getKeycodeHistory(user);
        if (history.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(history);
    }
}
