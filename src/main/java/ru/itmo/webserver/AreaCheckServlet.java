package ru.itmo.webserver;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.itmo.webserver.ControllerServlet.INDEX_JSP_PATH;

@WebServlet("/check")
public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем параметры X, Y и радиус R
        String xParam = request.getParameter("x");
        String yParam = request.getParameter("y");
        String rParam = request.getParameter("r");

        try {
            double x = parseAndValidateDouble(xParam);
            double y = parseAndValidateDouble(yParam);
            double r = parseAndValidateDouble(rParam);

            // Проверка корректности входных данных
            if (InputValidator.validateInputs(x, y, r)) {
                // Выполняем проверку попадания точки в область
                boolean isHit = checkPoint(x, y, r);

                // Сохраняем результаты в сессии пользователя
                HttpSession session = request.getSession();
                List<Result> results = null;
                Object resultsObj = session.getAttribute("results");
                if (resultsObj instanceof List<?>) {
                    results = (List<Result>) resultsObj;
                }
                if (results == null) {
                    results = new ArrayList<>();
                }
                results.add(new Result(x, y, r, isHit));
                session.setAttribute("results", results);

                // Устанавливаем атрибуты для передачи в JSP
                request.setAttribute("x", x);
                request.setAttribute("y", y);
                request.setAttribute("r", r);
                request.setAttribute("isHit", isHit);

                // Передаём управление на JSP для отображения результата
                request.getRequestDispatcher("/templates/result.jsp").forward(request, response);
            } else {
                throw new IllegalArgumentException("Некорректные значения координат или радиуса.");
            }
        } catch (IllegalArgumentException e) {
            // Обработка некорректных данных
            request.setAttribute("error", e.getMessage());
            request.setAttribute("x", xParam);
            request.setAttribute("y", yParam);
            request.setAttribute("r", rParam);
            request.getRequestDispatcher(INDEX_JSP_PATH).forward(request, response);
        }
    }

    // Метод для парсинга и проверки точности значений
    private double parseAndValidateDouble(String value) {
        try {
            double parsedValue = Double.parseDouble(value);
            if (Double.isInfinite(parsedValue)) {
                throw new IllegalArgumentException("Число выходит за пределы допустимого диапазона.");
            }
            return parsedValue;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат числа: " + value);
        }
    }

    // Проверка попадания точки в область
    public static boolean checkPoint(double x, double y, double r) {
        // Четверть круга в левом нижнем углу
        if (x <= 0 && y <= 0 && (x * x + y * y <= r * r)) {
            return true;
        }

        // Треугольник в верхнем левом углу
        if (x <= 0 && y >= 0 && y <= x + r) {
            return true;
        }

        // Прямоугольник в нижнем правом углу
        return x >= 0 && x <= r / 2 && y >= -r && y <= 0;
    }

    // Класс для хранения результатов
    @Value
    public static class Result {
        double x;
        double y;
        double r;
        boolean isHit;
    }

}
