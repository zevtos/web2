package ru.itmo.webserver.service;

import jakarta.servlet.http.HttpSession;
import ru.itmo.webserver.Result;

import java.util.ArrayList;
import java.util.List;

public class ResultService {

    public void saveResult(HttpSession session, Result result) {
        List<Result> results = (List<Result>) session.getAttribute("results");
        if (results == null) {
            results = new ArrayList<>();
        }
        results.add(result);
        session.setAttribute("results", results);
    }

    public List<Result> getResults(HttpSession session) {
        List<Result> results = (List<Result>) session.getAttribute("results");
        if (results == null) {
            results = new ArrayList<>();
        }
        return results;
    }
}
