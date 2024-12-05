package com.authentication.rbacApp.config;

import com.authentication.rbacApp.service.CustomUserDetailService;
import com.authentication.rbacApp.utility.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
// Import necessary classes for JWT processing and Spring Security

import java.io.IOException;
/**
 * This filter intercepts every HTTP request and validates the JWT token
 * provided in the Authorization header to authenticate the user.
 */

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;    // Utility class for JWT token operations

    @Autowired
    private CustomUserDetailService customUserDetailService;    // Service to load user details from the database

    /**
     * The core method of the filter that processes each request.
     *
     * @param request  the incoming HTTP request
     * @param response the HTTP response to be sent back
     * @param chain    the filter chain to pass the request and response along
     * @throws ServletException if an error occurs during the filter execution
     * @throws IOException      if an I/O error occurs during request handling
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
    throws ServletException, IOException {

        // Extract JWT token from the Authorization header
        final String authorizationHeader=request.getHeader("Authorization");
        String username=null;
        String token=null;

        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            // Remove the "Bearer " prefix to get the actual token
            token=authorizationHeader.substring(7);
            // Extract the username from the token
            username=jwtUtil.extractUsername(token);
        }

        // Validate the token and set authentication in the context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

            // Validate the token against the user's details
            if (jwtUtil.validateToken(token, userDetails.getUsername())) {
                // Create an authentication token for the user
                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Set additional details about the request
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Store the authentication in the Security Context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Continue with the filter chain for further processing
        chain.doFilter(request, response);


    }

}
