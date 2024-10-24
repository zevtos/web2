package ru.itmo.webserver.service;

import jakarta.servlet.http.HttpSession;
import ru.itmo.webserver.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ResultService - сервис для работы с результатами проверок попадания точек в область.
 * <p>
 * Этот класс предоставляет методы для сохранения результатов в сессии пользователя и получения их.
 */
public class ResultService {

    /**
     * Сохраняет результат проверки в сессии пользователя.
     *
     * @param session объект HttpSession, представляющий текущую сессию пользователя.
     * @param result  объект Result, который содержит информацию о проверке попадания точки в область.
     */
    public void saveResult(HttpSession session, Result result) {
        List<Result> results = getResults(session);
        synchronized (results) {
            results.add(result);
        }
    }

    /**
     * Получает список результатов из сессии пользователя. Если список еще не существует, создает новый.
     *
     * @param session объект HttpSession, представляющий текущую сессию пользователя.
     * @return список результатов, сохраненных в текущей сессии.
     */
    public List<Result> getResults(HttpSession session) {
        List<Result> results = (List<Result>) session.getAttribute("results");
        if (results == null) {
            results = Collections.synchronizedList(new ArrayList<>());
            session.setAttribute("results", results);
        }
        return results;
    }
}