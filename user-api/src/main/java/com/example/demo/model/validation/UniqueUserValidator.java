package com.example.demo.model.validation;

import org.springframework.stereotype.Component;

import com.example.demo.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueUserValidator 
    implements ConstraintValidator<UniqueUser, String> {

    private final UserRepository userRepository;

    public UniqueUserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return userRepository.findByEmail(email).isEmpty();
    }
    
}
