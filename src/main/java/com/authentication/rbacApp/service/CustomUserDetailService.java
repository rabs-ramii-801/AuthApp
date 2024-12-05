package com.authentication.rbacApp.service;


import com.authentication.rbacApp.model.User;
import com.authentication.rbacApp.repository.UserRepository;
import com.authentication.rbacApp.utility.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service implementation for loading user details during authentication.
 * Implements Spring Security's UserDetailsService interface.
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Loads a user by their username and returns a CustomUserDetails object.
     * This method is called during the authentication process.
     *
     * @param username the username of the user to be loaded
     * @return a UserDetails object containing the user's information
     * @throws UsernameNotFoundException if the user is not found in the database
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user by username using the userRepository
        User user = userRepository.findByUserName(username);

        // If no user is found, throw a UsernameNotFoundException
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Return a CustomUserDetails object with the user information
        return new CustomUserDetails(user);
    }
}
