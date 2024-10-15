package ru.itmo.webserver;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class InputValidationTest {

    // Тест на валидные входные данные
    @Test
    public void testValidInputs() {
        assertTrue(InputValidator.validateInputs(1, 2, 2.5), "Значения 1, 2, 2.5 должны быть корректными");
        assertTrue(InputValidator.validateInputs(-3, -5, 1), "Значения -3, -5, 1 должны быть корректными");
        assertTrue(InputValidator.validateInputs(5, 5, 3), "Значения 5, 5, 3 должны быть корректными");
    }

    // Тест на некорректные значения X
    @Test
    public void testInvalidX() {
        assertFalse(InputValidator.validateInputs(-6, 2, 3), "Значение X=-6 некорректно");
        assertFalse(InputValidator.validateInputs(6, 2, 3), "Значение X=6 некорректно");
    }

    // Тест на некорректные значения Y
    @Test
    public void testInvalidY() {
        assertFalse(InputValidator.validateInputs(1, -6, 3), "Значение Y=-6 некорректно");
        assertFalse(InputValidator.validateInputs(1, 6, 3), "Значение Y=6 некорректно");
    }

    // Тест на некорректные значения R
    @Test
    public void testInvalidR() {
        assertFalse(InputValidator.validateInputs(1, 2, 0.5), "Значение R=0.5 некорректно");
        assertFalse(InputValidator.validateInputs(1, 2, 4), "Значение R=4 некорректно");
    }
}
