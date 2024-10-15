package ru.itmo.webserver;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AreaCheckTest {

    // Тест на точку внутри круга
    @Test
    public void testPointInsideCircle() {
        assertTrue(AreaCheckServlet.checkPoint(-1, -1, 2), "Точка (-1,-1) должна быть внутри круга радиусом 2");
    }

    // Тест на точку в треугольнике
    @Test
    public void testPointInsideTriangle() {
        assertTrue(AreaCheckServlet.checkPoint(-1, 1, 2), "Точка (-1,1) должна быть внутри треугольника");
    }

    // Тест на точку в прямоугольнике
    @Test
    public void testPointInsideRectangle() {
        assertTrue(AreaCheckServlet.checkPoint(0.5, -1, 2), "Точка (0.5,-1) должна быть внутри прямоугольника");
    }

    // Тест на точку вне области
    @Test
    public void testPointOutsideArea() {
        assertFalse(AreaCheckServlet.checkPoint(3, 3, 2), "Точка (3,3) должна быть вне области");
    }
}
