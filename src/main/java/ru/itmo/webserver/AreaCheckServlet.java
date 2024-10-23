package ru.itmo.webserver;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itmo.webserver.service.InputService;
import ru.itmo.webserver.service.ResultService;

import java.io.IOException;

import static ru.itmo.webserver.ControllerServlet.INDEX_JSP_PATH;

@WebServlet("/check")
public class AreaCheckServlet extends HttpServlet {

    private final InputService inputService;
    private final ResultService resultService;

    public AreaCheckServlet() {
        this.inputService = new InputService();
        this.resultService = new ResultService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Получение параметров из атрибутов запроса
            String xParam = (String) request.getAttribute("x");
            String yParam = (String) request.getAttribute("y");
            String rParam = (String) request.getAttribute("r");

            // Парсинг и валидация данных
            Result result = inputService.parseAndValidateInput(xParam, yParam, rParam);

            // Проверка попадания точки в область
            result.setHit(inputService.checkPoint(result));

            // Синхронное сохранение результата в сессии
            HttpSession session = request.getSession();
            resultService.saveResult(session, result);

            // Установка атрибутов и передача в JSP
            request.setAttribute("x", result.getX());
            request.setAttribute("y", result.getY());
            request.setAttribute("r", result.getR());
            request.setAttribute("isHit", result.isHit());

            request.getRequestDispatcher("/templates/result.jsp").forward(request, response);

        } catch (IllegalArgumentException e) {
            // Обработка ошибок валидации и парсинга
            request.setAttribute("error", e.getMessage());
            request.setAttribute("x", request.getAttribute("x"));
            request.setAttribute("y", request.getAttribute("y"));
            request.setAttribute("r", request.getAttribute("r"));
            request.getRequestDispatcher(INDEX_JSP_PATH).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Произошла ошибка: " + e.getMessage());
            request.getRequestDispatcher(INDEX_JSP_PATH).forward(request, response);
        }
    }
}
