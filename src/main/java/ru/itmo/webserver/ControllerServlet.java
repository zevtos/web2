package ru.itmo.webserver;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * ControllerServlet - основной контроллер для обработки запросов.
 * <p>
 * Этот сервлет перенаправляет запросы на проверку попадания точки в область графика или
 * отображение начальной страницы в зависимости от наличия необходимых параметров в запросе.
 */
@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {

    public static final String INDEX_JSP_PATH = "/templates/index.jsp";
    private static final String AREA_CHECK_SERVLET_PATH = "/check";

    /**
     * Обрабатывает все типы HTTP-запросов, перенаправляя на соответствующую страницу или другой сервлет.
     *
     * @param request  объект HttpServletRequest, содержащий информацию о запросе от клиента.
     * @param response объект HttpServletResponse, который используется для отправки ответа клиенту.
     * @throws ServletException если произошла ошибка, связанная с сервлетом.
     * @throws IOException      если произошла ошибка ввода-вывода во время обработки запроса.
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String xParam = request.getParameter("x");
        String yParam = request.getParameter("y");
        String rParam = request.getParameter("r");

        if (areParametersMissing(xParam, yParam, rParam)) {
            forwardToIndexPage(request, response);
        } else {
            forwardToAreaCheck(request, response, xParam, yParam, rParam);
        }
    }

    /**
     * Проверяет, отсутствуют ли необходимые параметры x, y, или r в запросе.
     *
     * @param x значение параметра x.
     * @param y значение параметра y.
     * @param r значение параметра r.
     * @return true, если хотя бы один из параметров отсутствует; false в противном случае.
     */
    private boolean areParametersMissing(String x, String y, String r) {
        return x == null || y == null || r == null;
    }

    /**
     * Перенаправляет клиента на начальную страницу (index.jsp).
     *
     * @param request  объект HttpServletRequest, содержащий информацию о запросе от клиента.
     * @param response объект HttpServletResponse, который используется для отправки ответа клиенту.
     * @throws ServletException если произошла ошибка, связанная с сервлетом.
     * @throws IOException      если произошла ошибка ввода-вывода во время обработки запроса.
     */
    private void forwardToIndexPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(INDEX_JSP_PATH).forward(request, response);
    }

    /**
     * Перенаправляет запрос на AreaCheckServlet с параметрами x, y и r.
     *
     * @param request  объект HttpServletRequest, содержащий информацию о запросе от клиента.
     * @param response объект HttpServletResponse, который используется для отправки ответа клиенту.
     * @param x        значение параметра x.
     * @param y        значение параметра y.
     * @param r        значение параметра r.
     * @throws ServletException если произошла ошибка, связанная с сервлетом.
     * @throws IOException      если произошла ошибка ввода-вывода во время обработки запроса.
     */
    private void forwardToAreaCheck(HttpServletRequest request, HttpServletResponse response, String x, String y, String r) throws ServletException, IOException {
        request.setAttribute("x", x);
        request.setAttribute("y", y);
        request.setAttribute("r", r);
        request.getRequestDispatcher(AREA_CHECK_SERVLET_PATH).forward(request, response);
    }
}
