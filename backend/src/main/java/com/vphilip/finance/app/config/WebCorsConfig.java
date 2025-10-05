package com.vphilip.finance.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// This configuration class handles Cross-Origin Resource Sharing (CORS) settings for the web application
@Configuration
public class WebCorsConfig implements WebMvcConfigurer {

    // Injects allowed origins from properties file. These are the domains allowed to access the API
    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    // Defines allowed HTTP methods with default value if not specified in properties
    @Value("${cors.allowed-methods:GET,POST,PUT,DELETE,OPTIONS}")
    private String allowedMethods;

    // Defines allowed headers with default value '*' (all headers) if not specified
    @Value("${cors.allowed-headers:*}")
    private String allowedHeaders;

    // Controls whether credentials are allowed in CORS requests (default: true)
    @Value("${cors.allow-credentials:true}")
    private boolean allowCredentials;

    // Sets how long the CORS preflight response can be cached by browsers (default: 3600 seconds)
    @Value("${cors.max-age:3600}")
    private long maxAge;

    // Configures CORS mappings for all endpoints in the application
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")                                     // Applies to all paths
                .allowedOrigins(allowedOrigins.split(","))            // Converts comma-separated origins to array
                .allowedMethods(allowedMethods.split(","))            // Converts comma-separated methods to array
                .allowedHeaders(allowedHeaders.split(","))            // Converts comma-separated headers to array
                .allowCredentials(allowCredentials)                    // Sets credential support
                .maxAge(maxAge);                                      // Sets preflight cache duration
    }
}