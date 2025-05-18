package com.graduation_project.street2shelter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**") // Allow all paths
//                        .allowedOrigins("*") // Allow only this origin
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed methods
//                        .allowedHeaders("*") // Allow any headers
//                        .allowCredentials(true); // Allow cookies, Authorization header
//            }
//        };
//    }


    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        // Choose ONE of these origin options:

                        // Option 1: Specific origins (recommended for production)
                        .allowedOrigins("http://localhost", "http://127.0.0.1")

                        // Option 2: Use allowedOriginPatterns for dynamic matching
                        .allowedOriginPatterns("*")

                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600); // 1 hour cache
            }
        };
    }
}
