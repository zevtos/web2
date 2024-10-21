//package ru.itmo.webserver;
//
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//import static ru.itmo.webserver.ControllerServlet.INDEX_JSP_PATH;
//
//public class AreaCheckServletTest {
//
//    @Mock
//    private HttpServletRequest request;
//
//    @Mock
//    private HttpServletResponse response;
//
//    @Mock
//    private HttpSession session;
//
//    @Mock
//    private RequestDispatcher requestDispatcher;
//
//    @InjectMocks
//    private AreaCheckServlet servlet;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testDoGet_ValidParameters_HitArea() throws ServletException, IOException {
//        // Мокаем параметры запроса
//        when(request.getParameter("x")).thenReturn("0");
//        when(request.getParameter("y")).thenReturn("-0.92");
//        when(request.getParameter("r")).thenReturn("1.5");
//        when(request.getSession()).thenReturn(session);
//        when(request.getRequestDispatcher("/templates/result.jsp")).thenReturn(requestDispatcher);
//
//        // Выполнение сервлета
//        servlet.doGet(request, response);
//
//        // Проверка установки атрибутов и вызова forward
//        verify(request).setAttribute("x", 0.0);
//        verify(request).setAttribute("y", -0.92);
//        verify(request).setAttribute("r", 1.5);
//        verify(request).setAttribute("isHit", true);  // Убедитесь, что результат проверен на попадание
//        verify(requestDispatcher).forward(request, response);
//    }
//
//    @Test
//    public void testDoGet_ValidParameters_MissArea() throws ServletException, IOException {
//        // Мокаем параметры запроса
//        when(request.getParameter("x")).thenReturn("2");
//        when(request.getParameter("y")).thenReturn("-4");
//        when(request.getParameter("r")).thenReturn("1");
//        when(request.getSession()).thenReturn(session);
//        when(request.getRequestDispatcher("/templates/result.jsp")).thenReturn(requestDispatcher);
//
//        // Выполнение сервлета
//        servlet.doGet(request, response);
//
//        // Проверка установки атрибутов и вызова forward
//        verify(request).setAttribute("x", 2.0);
//        verify(request).setAttribute("y", -4.0);
//        verify(request).setAttribute("r", 1.0);
//        verify(request).setAttribute("isHit", false);
//        verify(requestDispatcher).forward(request, response);
//    }
//
//    @Test
//    public void testDoGet_InvalidParameters() throws ServletException, IOException {
//        // Мокаем параметры запроса с недопустимыми значениями
//        when(request.getParameter("x")).thenReturn("abc");
//        when(request.getParameter("y")).thenReturn("1");
//        when(request.getParameter("r")).thenReturn("2");
//        when(request.getRequestDispatcher(INDEX_JSP_PATH)).thenReturn(requestDispatcher);
//
//        // Выполнение сервлета
//        servlet.doGet(request, response);
//
//        // Проверка установки атрибутов ошибки и вызова forward
//        verify(request).setAttribute(eq("error"), contains("Некорректный формат числа"));
//        verify(request).setAttribute("x", "abc");
//        verify(request).setAttribute("y", "1");
//        verify(request).setAttribute("r", "2");
//        verify(requestDispatcher).forward(request, response);
//    }
//
//    @Test
//    public void testDoGet_MissingParameters() throws ServletException, IOException {
//        // Мокаем параметры запроса с отсутствующим параметром
//        when(request.getParameter("x")).thenReturn(null);
//        when(request.getParameter("y")).thenReturn("1");
//        when(request.getParameter("r")).thenReturn("2");
//        when(request.getRequestDispatcher(INDEX_JSP_PATH)).thenReturn(requestDispatcher);
//
//        // Выполнение сервлета
//        servlet.doGet(request, response);
//
//        // Проверка, что перенаправление произошло на index.jsp
//        verify(request).getRequestDispatcher(INDEX_JSP_PATH);
//        verify(requestDispatcher).forward(request, response);
//    }
//
//    @Test
//    public void testDoGet_ZeroValue() throws ServletException, IOException {
//        // Мокаем параметры запроса с значением нуля
//        when(request.getParameter("x")).thenReturn("0");
//        when(request.getParameter("y")).thenReturn("0");
//        when(request.getParameter("r")).thenReturn("1.5");
//        when(request.getSession()).thenReturn(session);
//        when(request.getRequestDispatcher("/templates/result.jsp")).thenReturn(requestDispatcher);
//
//        // Выполнение сервлета
//        servlet.doGet(request, response);
//
//        // Проверка установки атрибутов и вызова forward
//        verify(request).setAttribute("x", 0.0);
//        verify(request).setAttribute("y", 0.0);
//        verify(request).setAttribute("r", 1.5);
//        verify(request).setAttribute("isHit", true);
//        verify(requestDispatcher).forward(request, response);
//    }
//
//    @Test
//    public void testCheckPoint() {
//        // Проверка попадания в четверть круга
//        assertTrue(AreaCheckServlet.checkPoint(-1, -1, 2));
//
//        // Проверка попадания в треугольник
//        assertTrue(AreaCheckServlet.checkPoint(-1, 1, 3));
//
//        // Проверка попадания в прямоугольник
//        assertTrue(AreaCheckServlet.checkPoint(0.5, -2, 5));
//
//        // Проверка точки вне всех областей
//        assertFalse(AreaCheckServlet.checkPoint(5, 5, 1));
//    }
//}
