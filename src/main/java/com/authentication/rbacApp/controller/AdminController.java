package com.authentication.rbacApp.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    // Example of a protected admin route
    @GetMapping("/dashboard")
    public String getAdminDashboard() {
        return "Welcome to the Admin Dashboard!";
    }

    // Another example of an admin route
    @PostMapping("/create")
    public String createAdminResource(@RequestBody String data) {
        return "Admin resource created with data: " + data;
    }
}
