package com.keycodehelp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class logoutController {

    @GetMapping("/logout")
    public String logout() {
        //Custom logic will go here
        return "redirect:/login?logout"; // Redirect to login page
    }
}
