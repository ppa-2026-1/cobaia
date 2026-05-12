package com.example.demo.transversal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.model.AuthService;
import com.example.demo.transversal.auth.AuthFilter;

@Configuration
public class SecurityConfig {

    @Bean
    AuthFilter authFilter(AuthService authService) {
        return new AuthFilter(authService);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthFilter authFilter) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/auth/**").permitAll() 
                    .anyRequest().authenticated())
                .headers(headers -> headers
                .frameOptions(cust -> cust.sameOrigin()))
                .httpBasic(basic -> basic.disable())
                .formLogin(login -> login.disable())
                .build();
    }
}
