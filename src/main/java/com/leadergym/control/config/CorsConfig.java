package com.leadergym.control.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Allow patterns for Railway domains, GitHub Pages, and localhost
        config.setAllowedOriginPatterns(List.of(
                "https://*.up.railway.app",
                "https://joaquin-labrador.github.io",
                "http://localhost:5173",
                "http://localhost:3000",
                "http://localhost:4200",
                "http://localhost:8080",
                "http://localhost:5174"
        ));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "Origin", "X-Requested-With"));
        config.setExposedHeaders(List.of("Location", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        config.setAllowCredentials(false); // No cookies/session

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
