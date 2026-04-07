package com.example.demo.transversal.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AtLeastOneValidator 
    implements ConstraintValidator<AtLeastOne, Iterable<?>> {

    @Override
    public boolean isValid(Iterable<?> value, ConstraintValidatorContext context) {
        return value != null && value.iterator().hasNext();
    }
    
}
