package com.authentication.rbacApp.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * Utility class for handling JWT (JSON Web Tokens) operations.
 * Provides methods to generate tokens, extract claims, validate tokens, and check expiration.
 */

@Component
public class JwtUtil {

    // Secret key used for signing and verifying JWT tokens
     private final String secretKey="s3ev3xqvRbEP6dBax6hWdFcZO/F54kJyf6++OB0chV4=";

        /**
         * Generates a JWT token with the provided username and role.
         *
         * @param username the username to include in the token
         * @param role the role of the user to include in the token
         * @return the generated JWT token as a string
         */
     public String generateToken(String username,String role){
         return Jwts.builder()
                 .setSubject(username)  // Set the username as the subject
                 .claim("role",role)    // Add role as a custom claim
                 .setIssuedAt(new Date())   // Set the token issue time
                 .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10)) // Set expiration (10 hours)
                 .signWith(SignatureAlgorithm.HS256,secretKey)  // Sign the token with the secret key using HS256
                 .compact();    // Generate the compact JWT string
     }
        /**
         * Extracts all claims from a given JWT token.
         *
         * @param token the JWT token
         * @return the claims contained in the token
         */

     public Claims extractClaims(String token){

         return Jwts.parser()
                 .setSigningKey(secretKey)  // Use the secret key to parse the token
                 .parseClaimsJws(token).getBody();  // Extract the claims from the token

     }

        /**
         * Extracts the username (subject) from a given JWT token.
         *
         * @param token the JWT token
         * @return the username contained in the token
         */
     public String extractUsername(String token){
         return extractClaims(token).getSubject();
     }


        /**
         * Extracts the role from a given JWT token.
         *
         * @param token the JWT token
         * @return the role contained in the token
         */
     public String extractRole(String token){
         return extractClaims(token).get("role",String.class);
     }
        /**
         * Validates the JWT token by comparing the username and checking if the token is expired.
         *
         * @param token the JWT token
         * @param username the username to compare with the token's subject
         * @return true if the token is valid, false otherwise
         */
     public boolean validateToken(String token,String username){
         return username.equals(extractUsername(token)) && !isTokenExpired(token);
     }
        /**
         * Checks if the JWT token has expired.
         *
         * @param token the JWT token
         * @return true if the token is expired, false otherwise
         */

     public boolean isTokenExpired(String token){
         return extractClaims(token).getExpiration().before(new Date());
     }
}
