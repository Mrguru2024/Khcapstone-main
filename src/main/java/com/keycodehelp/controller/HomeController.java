package com.keycodehelp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String homePage(Model model) {
        return "/home";  // return home.html located in the templates folder
    }

    @GetMapping("/keycode")
    public String keycodePage(Model model) {
        return "/keycode";  // return keycode.html located in the templates folder
    }

    @GetMapping("/vin-lookup")
    public String vinLookupPage(Model model) {
        return "/vin-lookup";  // return vin-lookup.html located in the templates folder
    }

    @GetMapping("/about")
    public String aboutPage(Model model) {
        return "/about";  // return about.html located in the templates folder
    }

    @GetMapping("/contact")
    public String contactPage(Model model) {
        return "/contact";  // return contact.html located in the templates folder
    }
}
