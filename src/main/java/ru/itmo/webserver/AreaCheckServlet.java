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

/**
 * Servlet для проверки попадания точки в заданную область на графике.
 * <p>
 * Обрабатывает GET-запросы на адрес "/check", парсит и валидирует входные параметры,
 * проверяет попадание точки в область и сохраняет результаты в сессии.
 */
@WebServlet("/check")
public class AreaCheckServlet extends HttpServlet {

    private final InputService inputService = new InputService();
    private final ResultService resultService = new ResultService();

    /**
     * Обрабатывает GET-запросы, выполняет валидацию входных данных,
     * проверку попадания точки в область и сохранение результата в сессии.
     *
     * @param request  запрос, содержащий параметры x, y, r
     * @param response ответ, который будет направлен обратно клиенту
     * @throws ServletException если произошла ошибка обработки запроса
     * @throws IOException      если произошла ошибка ввода-вывода
     */
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
            // Общая обработка ошибок
            request.setAttribute("error", "Произошла ошибка: " + e.getMessage());
            request.getRequestDispatcher(INDEX_JSP_PATH).forward(request, response);
        }
    }
}
