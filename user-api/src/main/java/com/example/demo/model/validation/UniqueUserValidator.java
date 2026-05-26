package com.example.demo.model.validation;

import org.springframework.stereotype.Component;

import com.example.demo.repository.JpaUserRepositoryAdapter;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueUserValidator 
    implements ConstraintValidator<UniqueUser, String> {

    private final JpaUserRepositoryAdapter userRepository;

    public UniqueUserValidator(JpaUserRepositoryAdapter userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return userRepository.findByEmail(email).isEmpty();
    }
    
}
