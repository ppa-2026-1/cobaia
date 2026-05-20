package com.example.demo.model.validation;

import org.springframework.stereotype.Component;

import com.example.demo.repository.SqlUserRepositoryAdapter;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueUserValidator 
    implements ConstraintValidator<UniqueUser, String> {

    private final SqlUserRepositoryAdapter userRepository;

    public UniqueUserValidator(SqlUserRepositoryAdapter userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return userRepository.findByEmail(email).isEmpty();
    }
    
}
