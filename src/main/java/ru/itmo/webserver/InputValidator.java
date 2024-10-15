package ru.itmo.webserver;

public class InputValidator {

    // Метод для валидации данных
    public static boolean validateInputs(double x, double y, double r) {
        boolean isXValid = (x == -3 || x == -2 || x == -1 || x == 0 || x == 1 || x == 2 || x == 3 || x == 4 || x == 5);
        boolean isYValid = y >= -5 && y <= 5;
        boolean isRValid = (r == 1 || r == 1.5 || r == 2 || r == 2.5 || r == 3);
        return isXValid && isYValid && isRValid;
    }
}
