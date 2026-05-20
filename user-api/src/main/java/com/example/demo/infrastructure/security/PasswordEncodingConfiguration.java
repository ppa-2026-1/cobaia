package com.example.demo.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncodingConfiguration {
    
    @Bean
    PasswordEncoder passwordEncoder() { // ABSTRATO
        return new BCryptPasswordEncoder(); // CONCRETO (pode ser alterado)
    }
}
