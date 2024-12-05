package com.authentication.rbacApp.controller;

import com.authentication.rbacApp.model.User;
import com.authentication.rbacApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public String getUserProfile() {
        return "Welcome to your user profile!";
    }

    // Another example of a user route
    @PostMapping("/update")
    public String updateUserProfile(@RequestBody String updatedData) {
        return "User profile updated with: " + updatedData;
    }
}
