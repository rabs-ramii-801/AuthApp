package com.authentication.rbacApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// Import necessary Spring framework and configuration classes

/**
 * This configuration class enables and customizes Cross-Origin Resource Sharing (CORS) settings
 * for the application, allowing controlled interaction between frontend and backend servers
 * running on different domains.
 */

@Configuration
public class CorsConfig {

    /**
     * Creates a CORS configuration bean to define allowed origins, HTTP methods,
     * headers, and other properties for handling cross-origin requests.
     *
     * @return a WebMvcConfigurer instance with the specified CORS settings.
     */

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {

            /**
             * Overrides the default CORS mappings to apply custom settings for all endpoints.
             *
             * @param registry the CorsRegistry object used to define CORS rules.
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Apply CORS to all endpoints
                        .allowedOrigins("http://localhost:4200") // Allow Angular app
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true); // Allow credentials like cookies
            }
        };
    }
}
