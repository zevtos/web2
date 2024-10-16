package ru.itmo.webserver.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class ValidRValidator implements ConstraintValidator<ValidR, Double> {
    private final Set<Double> validRValues = Set.of(1.0, 1.5, 2.0, 2.5, 3.0);

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value != null && validRValues.contains(value);
    }
}
