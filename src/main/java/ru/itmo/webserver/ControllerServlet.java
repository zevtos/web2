package ru.itmo.webserver;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {

    public static final String INDEX_JSP_PATH = "/templates/index.jsp";
    private static final String AREA_CHECK_SERVLET_PATH = "/check";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем параметры X, Y, R из запроса
        String xParam = request.getParameter("x");
        String yParam = request.getParameter("y");
        String rParam = request.getParameter("r");

        // Если параметры не переданы (null), отображаем форму
        if (xParam == null || yParam == null || rParam == null) {
            // Перенаправляем на index.jsp, если параметры отсутствуют
            request.getRequestDispatcher(INDEX_JSP_PATH).forward(request, response);
            return;
        }

        // Перенаправляем запрос на AreaCheckServlet для проверки попадания точки
        response.sendRedirect(request.getContextPath() + AREA_CHECK_SERVLET_PATH + "?x=" + xParam + "&y=" + yParam + "&r=" + rParam);
    }
}
