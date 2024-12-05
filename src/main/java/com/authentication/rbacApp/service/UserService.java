package com.authentication.rbacApp.service;

import com.authentication.rbacApp.model.Role;
import com.authentication.rbacApp.model.User;
import com.authentication.rbacApp.repository.RoleRepositry;
import com.authentication.rbacApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepositry roleRepositry;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerUser(String username, String name, String password, Set<Role> roles) {
        // Check if username already exists
        Optional<User> existingUser = findByUsername(username);
        if (existingUser.isPresent()) {
            return "Username already exists."; // Return message if user exists
        }

        // Filter valid roles from the provided roles
        Set<Role> validRoles = new HashSet<>();
        for (Role role : roles) {
            Role existingRole = roleService.getRole(role.getRoleName());  // Assuming getRole takes role name
            if (existingRole != null) {
                validRoles.add(existingRole); // Add the role if it exists
            } else {
                // Handle the case where the role doesn't exist (optional)
                // For example, you can log a warning or return a message
                System.out.println("Role " + role.getRoleName() + " does not exist.");
                roleService.createRole(role.toString());
                validRoles.add(role);
            }
        }

        if (validRoles.isEmpty()) {
            return "No valid roles provided."; // Return if no valid roles found
        }

        // Create a new user object with validated roles
        User user = new User();
        user.setUserName(username);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(validRoles); // Set the valid roles to the user

        // Save the user in the repository
        userRepository.save(user);
        return "User registered successfully."; // Return success message
    }


    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUserName(username));
    }
}
