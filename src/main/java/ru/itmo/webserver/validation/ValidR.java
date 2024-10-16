package ru.itmo.webserver.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ValidRValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface ValidR {
    String message() default "Invalid value for r. Must be one of 1, 1.5, 2, 2.5, 3.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
