package ru.itmo.webserver.service;

import jakarta.servlet.http.HttpSession;
import ru.itmo.webserver.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultService {

    public void saveResult(HttpSession session, Result result) {
        List<Result> results = getResults(session);
        synchronized (results) {
            results.add(result);
        }
    }

    public List<Result> getResults(HttpSession session) {
        List<Result> results = (List<Result>) session.getAttribute("results");
        if (results == null) {
            results = Collections.synchronizedList(new ArrayList<>());
            session.setAttribute("results", results);
        }
        return results;
    }
}
