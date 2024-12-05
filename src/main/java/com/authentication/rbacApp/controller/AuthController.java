package com.authentication.rbacApp.controller;

import com.authentication.rbacApp.model.Login;
import com.authentication.rbacApp.model.Role;
import com.authentication.rbacApp.model.User;
import com.authentication.rbacApp.service.UserService;
import com.authentication.rbacApp.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    /**
     * Login endpoint to authenticate a user and generate a JWT token.
     *
     * @param loginDetails contains the username and password for authentication
     * @return the generated JWT token if authentication is successful
     */

    @PostMapping("/login")
    public String login(@RequestBody Login loginDetails) {
        String username = loginDetails.getUsername();
        String password = loginDetails.getPassword();

        // Authenticate the user by validating the username and password
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // Retrieve the user from the database
        var user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        // Get the role of the user (assuming one role per user)
        String role = user.getRoles().iterator().next().getRoleName();

        // Generate and return the JWT token for the authenticated user
        return jwtUtil.generateToken(username, role);
    }

    /**
     * Register endpoint to create a new user.
     *
     * @param registrationRequest contains the user details for registration
     * @return a success message in JSON format
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User registrationRequest) {
        // Extract data from the incoming User object
        String username = registrationRequest.getUserName();
        String name = registrationRequest.getName();
        String password = registrationRequest.getPassword();

        // Assuming roles are passed as a Set<Role>
        Set<Role> roles = registrationRequest.getRoles();

        // Call the service to register the user
        String message=userService.registerUser(username, name, password,roles);
        // Return a JSON object with a success message
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
