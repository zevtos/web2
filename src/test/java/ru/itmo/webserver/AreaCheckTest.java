package ru.itmo.webserver;

import org.junit.jupiter.api.Test;
import ru.itmo.webserver.service.InputService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AreaCheckTest {
    public static InputService inputService = new InputService();

    // Тест на точку внутри круга
    @Test
    public void testPointInsideCircle() {
        assertTrue(inputService.checkPoint(-1, -1, 2), "Точка (-1,-1) должна быть внутри круга радиусом 2");
    }

    // Тест на точку в треугольнике
    @Test
    public void testPointInsideTriangle() {
        assertTrue(inputService.checkPoint(-1, 1, 2), "Точка (-1,1) должна быть внутри треугольника");
    }

    // Тест на точку в прямоугольнике
    @Test
    public void testPointInsideRectangle() {
        assertTrue(inputService.checkPoint(0.5, -1, 2), "Точка (0.5,-1) должна быть внутри прямоугольника");
    }

    // Тест на точку вне области
    @Test
    public void testPointOutsideArea() {
        assertFalse(inputService.checkPoint(3, 3, 2), "Точка (3,3) должна быть вне области");
    }
}
