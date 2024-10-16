package ru.itmo.webserver.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ValidXValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface ValidX {
    String message() default "Invalid value for x. Must be one of -3, -2, -1, 0, 1, 2, 3, 4, 5.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
