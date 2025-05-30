package com.aasoo.basic_auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(httpSecurityCsrfConfigurer -> {
                            httpSecurityCsrfConfigurer.disable(); // Disable CSRF protection for simplicity
                        }
                ) // Disable CSRF for simplicity, not recommended for production
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/").permitAll() // Allow access to /info without authentication
                                .anyRequest().authenticated() // Require authentication for all other requests
                )
                .httpBasic(Customizer.withDefaults())// Use basic authentication
                .formLogin(Customizer.withDefaults()); // Enable form login

        return http.build();
    }
}
