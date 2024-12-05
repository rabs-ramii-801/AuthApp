package com.authentication.rbacApp.utility;

import com.authentication.rbacApp.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Custom implementation of the Spring Security `UserDetails` interface.
 * Represents the user details used for authentication and authorization in the application.
 */
public class CustomUserDetails implements UserDetails {
    private final String username;  // Username of the user
    private final String password;  // Password of the user
    private final Set<GrantedAuthority> authorities;    // Set of roles/authorities assigned to the user

    /**
     * Constructor that maps the application's `User` model to the `UserDetails` interface.
     *
     * @param user the `User` object containing the user's information
     */
    public CustomUserDetails(User user) {
        this.username = user.getUserName(); // Map username from User model
        this.password = user.getPassword(); // Map password from User model

        // Convert roles in the User model to Spring Security's `GrantedAuthority`
        this.authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toSet());
    }

    /**
     * Returns the authorities granted to the user.
     *
     * @return a collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    /**
     * Returns the password used to authenticate the user.
     *
     * @return the user's password
     */
    @Override
    public String getPassword() {
        return password;
    }
    /**
     * Returns the username used to authenticate the user.
     *
     * @return the user's username
     */
    @Override
    public String getUsername() {
        return username;
    }
    /**
     * Indicates whether the user's account has expired.
     *
     * @return `true` if the account is not expired, `false` otherwise
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * Indicates whether the user is locked or unlocked.
     *
     * @return `true` if the account is not locked, `false` otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * Indicates whether the user's credentials (e.g., password) have expired.
     *
     * @return `true` if credentials are not expired, `false` otherwise
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return `true` if the user is enabled, `false` otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;    // Always return true (customize if needed)
    }
}
