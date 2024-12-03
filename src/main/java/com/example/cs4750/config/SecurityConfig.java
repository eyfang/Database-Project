// src/main/java/com/example/cs4750/config/SecurityConfig.java
package com.example.cs4750.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/movies/**").permitAll()
                        .requestMatchers("/api/watchlists/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/watchlists/**").permitAll()
                        .requestMatchers("/api/reviews/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}