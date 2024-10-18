package com.keycodehelp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/welcome")
    public Map<String, String> welcomeMessage() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome to Keycode Help Backend!");
        return response;
    }
}
