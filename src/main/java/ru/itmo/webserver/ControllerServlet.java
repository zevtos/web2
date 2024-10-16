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

        if (areParametersMissing(xParam, yParam, rParam)) {
            forwardToIndexPage(request, response);
        } else {
            redirectToAreaCheck(request, response, xParam, yParam, rParam);
        }
    }

    private boolean areParametersMissing(String x, String y, String r) {
        return x == null || y == null || r == null;
    }

    // Метод для перенаправления на index.jsp
    private void forwardToIndexPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(INDEX_JSP_PATH).forward(request, response);
    }

    // Метод для перенаправления запроса на AreaCheckServlet с параметрами
    private void redirectToAreaCheck(HttpServletRequest request, HttpServletResponse response, String x, String y, String r) throws IOException {
        String redirectUrl = buildRedirectUrl(request.getContextPath(), x, y, r);
        response.sendRedirect(redirectUrl);
    }

    // Метод для построения URL с параметрами для перенаправления
    private String buildRedirectUrl(String contextPath, String x, String y, String r) {
        return String.format("%s%s?x=%s&y=%s&r=%s", contextPath, ControllerServlet.AREA_CHECK_SERVLET_PATH, x, y, r);
    }
}
