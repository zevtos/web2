package ru.itmo.webserver;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputValidationTest {

    private final Validator validator;

    public InputValidationTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // Тест на валидные входные данные
    @Test
    public void testValidInputs() {
        Result validResult1 = new Result(null, 1, 2, 2.5, true);
        Result validResult2 = new Result(null, -3, -5, 1, true);
        Result validResult3 = new Result(null, 5, 5, 3, true);

        assertTrue(validator.validate(validResult1).isEmpty(), "Значения 1, 2, 2.5 должны быть корректными");
        assertTrue(validator.validate(validResult2).isEmpty(), "Значения -3, -5, 1 должны быть корректными");
        assertTrue(validator.validate(validResult3).isEmpty(), "Значения 5, 5, 3 должны быть корректными");
    }

    // Тест на некорректные значения X
    @Test
    public void testInvalidX() {
        Result invalidResult1 = new Result(null, -6, 2, 3, true);
        Result invalidResult2 = new Result(null, 6, 2, 3, true);

        Set<ConstraintViolation<Result>> violations1 = validator.validate(invalidResult1);
        Set<ConstraintViolation<Result>> violations2 = validator.validate(invalidResult2);

        assertFalse(violations1.isEmpty(), "Значение X=-6 некорректно, но ошибок не найдено");
        assertFalse(violations2.isEmpty(), "Значение X=6 некорректно, но ошибок не найдено");
    }

    // Тест на некорректные значения Y
    @Test
    public void testInvalidY() {
        Result invalidResult1 = new Result(null, 1, -6, 3, true);
        Result invalidResult2 = new Result(null, 1, 6, 3, true);

        Set<ConstraintViolation<Result>> violations1 = validator.validate(invalidResult1);
        Set<ConstraintViolation<Result>> violations2 = validator.validate(invalidResult2);

        assertFalse(violations1.isEmpty(), "Значение Y=-6 некорректно, но ошибок не найдено");
        assertFalse(violations2.isEmpty(), "Значение Y=6 некорректно, но ошибок не найдено");
    }

    // Тест на некорректные значения R
    @Test
    public void testInvalidR() {
        Result invalidResult1 = new Result(null, 1, 2, 0.5, true);
        Result invalidResult2 = new Result(null, 1, 2, 4, true);

        Set<ConstraintViolation<Result>> violations1 = validator.validate(invalidResult1);
        Set<ConstraintViolation<Result>> violations2 = validator.validate(invalidResult2);

        assertFalse(violations1.isEmpty(), "Значение R=0.5 некорректно, но ошибок не найдено");
        assertFalse(violations2.isEmpty(), "Значение R=4 некорректно, но ошибок не найдено");
    }
}
