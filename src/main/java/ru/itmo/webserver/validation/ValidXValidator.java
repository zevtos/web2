package ru.itmo.webserver.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class ValidXValidator implements ConstraintValidator<ValidX, Double> {
    private final Set<Double> validXValues = Set.of(-3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0);

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value != null && validXValues.contains(value);
    }
}
