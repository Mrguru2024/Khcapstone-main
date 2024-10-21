// LoginController for handling login and session management requests
package com.keycodehelp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "sessionExpired", required = false) String sessionExpired,
                                Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        if (sessionExpired != null) {
            model.addAttribute("sessionExpired", "Your session has expired. Please log in again.");
        }
        return "/login";  // Returns login.html template
    }
}
