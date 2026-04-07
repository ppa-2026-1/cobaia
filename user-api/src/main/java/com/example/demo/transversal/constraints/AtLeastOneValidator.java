package com.example.demo.transversal.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AtLeastOneValidator implements ConstraintValidator<AtLeastOne, Iterable<?>> {

    @Override
    public boolean isValid(Iterable<?> value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return value.iterator().hasNext();
    }

    
    
}
