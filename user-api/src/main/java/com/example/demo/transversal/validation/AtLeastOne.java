package com.example.demo.transversal.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;



// ao menos um

// IA é extreme declarative programming

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = AtLeastOneValidator.class)
public @interface AtLeastOne {
    String message() default "Deve ter pelo menos um item";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}









class CPFValidator implements ConstraintValidator<CPF, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        return cpf.equals("000000000000");
    }

}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = CPFValidator.class)
@interface CPF {
    String message() default "Deve ser um CPF válido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class Cliente {
    @CPF
    String cpf;
}
