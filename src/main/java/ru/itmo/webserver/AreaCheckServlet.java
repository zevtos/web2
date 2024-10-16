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
        String xParam = request.getParameter("x");
        String yParam = request.getParameter("y");
        String rParam = request.getParameter("r");

        try {
            // Парсинг и валидация данных через InputService
            Result result = inputService.parseAndValidateInput(xParam, yParam, rParam);

            // Проверка попадания точки в область
            result.setHit(inputService.checkPoint(result));

            // Сохранение результата в сессии
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
            request.setAttribute("x", xParam);
            request.setAttribute("y", yParam);
            request.setAttribute("r", rParam);
            request.getRequestDispatcher(INDEX_JSP_PATH).forward(request, response);
        } catch (Exception e) {
            // Общий перехват для непредвиденных ошибок
            request.setAttribute("error", "Произошла ошибка: " + e.getMessage());
            request.getRequestDispatcher(INDEX_JSP_PATH).forward(request, response);
        }
    }
}
