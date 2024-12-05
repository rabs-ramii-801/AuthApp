package com.authentication.rbacApp.config;

import com.authentication.rbacApp.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// Importing necessary classes for Spring Security and JWT configuration

/**
 * SecurityConfig configures the application's security settings,
 * including authentication, authorization, and password encoding.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Injecting CustomUserDetailService to handle user authentication logic
    @Autowired
    private final CustomUserDetailService userDetailService;


    // Injecting JwtRequestFilter for processing JWT tokens
    @Autowired
    private final JwtRequestFilter jwtAuthenticationFilter;

    // Constructor-based dependency injection
    public SecurityConfig(CustomUserDetailService userDetailsService, JwtRequestFilter jwtAuthenticationFilter) {
        this.userDetailService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configures a password encoder bean to hash and verify passwords securely.
     * BCrypt is used as it is a strong hashing algorithm.
     *
     * @return the password encoder instance
     */

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the authentication manager with the custom user details service and password encoder.
     *
     * @param httpSecurity the HttpSecurity object to build the shared AuthenticationManagerBuilder
     * @param passwordEncoder the password encoder to be used
     * @return the configured authentication manager
     * @throws Exception if an error occurs during configuration
     */

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder)throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder =
                httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();

    }
    /**
     * Configures the security filter chain, defining the access rules for various endpoints
     * and integrating the JWT filter.
     *
     * @param http the HttpSecurity object to configure the security settings
     * @return the configured security filter chain
     * @throws Exception if an error occurs during configuration
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disabling CSRF and configuring URL-based access
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/auth/**", "/roles/**").permitAll()// Allow authentication and roles routes
                                .requestMatchers("/auth/register").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN") // Only allow admin role for /admin/** routes
                                .requestMatchers("/user/**").hasRole("USER") // Only allow user role for /user/** routes// Allow authentication and roles routes
                                .anyRequest().authenticated() // Require authentication for other routes
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Stateless authentication

        // Add JWT filter before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();    // Build and return the security filter chain
    }
}
