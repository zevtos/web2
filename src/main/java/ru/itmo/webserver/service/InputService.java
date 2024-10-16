package ru.itmo.webserver.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import ru.itmo.webserver.Result;

import java.util.Set;

public class InputService {

    private final Validator validator;

    public InputService() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public Result parseAndValidateInput(String xParam, String yParam, String rParam) {
        try {
            double x = Double.parseDouble(xParam);
            double y = Double.parseDouble(yParam);
            double r = Double.parseDouble(rParam);

            Result result = new Result(null, x, y, r, false);

            // Выполнение валидации
            Set<ConstraintViolation<Result>> violations = validator.validate(result);
            if (!violations.isEmpty()) {
                StringBuilder errorMessages = new StringBuilder();
                for (ConstraintViolation<Result> violation : violations) {
                    errorMessages.append(violation.getMessage()).append("<br>");
                }
                throw new IllegalArgumentException(errorMessages.toString());
            }

            return result;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат числового значения.");
        }
    }

    public boolean checkPoint(Result result) {
        return checkPoint(result.getX(), result.getY(), result.getR());
    }

    public boolean checkPoint(double x, double y, double r) {
        // Четверть круга в левом нижнем углу
        if (x <= 0 && y <= 0 && (x * x + y * y <= r * r)) {
            return true;
        }

        // Треугольник в верхнем левом углу
        if (x <= 0 && y >= 0 && y <= x + r) {
            return true;
        }

        // Прямоугольник в нижнем правом углу
        return x >= 0 && x <= r / 2 && y >= -r && y <= 0;
    }
}
