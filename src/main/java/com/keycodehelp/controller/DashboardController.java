//package com.keycodehelp.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class DashboardController {
//
//    // Admin dashboard
//    @GetMapping("/admin")
//    public String adminDashboard(Model model) {
//        // You can add attributes to the model to pass data to the view
//        model.addAttribute("pageTitle", "Admin Dashboard");
//        return "admin";  // This will render admin.html
//    }
//
//    // User dashboard
//    @GetMapping("/user")
//    public String userDashboard(Model model) {
//        // Add user-specific data to the model
//        model.addAttribute("pageTitle", "User Dashboard");
//        return "user";  // This will render user-dashboard.html
//    }
//}
