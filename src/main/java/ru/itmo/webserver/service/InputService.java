package ru.itmo.webserver.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import ru.itmo.webserver.Result;

import java.util.Set;

/**
 * InputService - сервис для парсинга и валидации входных данных.
 * <p>
 * Этот класс предоставляет методы для парсинга параметров, пришедших от клиента,
 * валидации данных и проверки попадания точки в область на графике.
 */
public class InputService {

    private final Validator validator;

    /**
     * Конструктор класса InputService, инициализирующий валидатор для проверки данных.
     */
    public InputService() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    /**
     * Парсит входные параметры x, y и r, затем выполняет валидацию данных.
     *
     * @param xParam строковое значение параметра x, пришедшее от клиента.
     * @param yParam строковое значение параметра y, пришедшее от клиента.
     * @param rParam строковое значение параметра r, пришедшее от клиента.
     * @return объект Result, содержащий значения x, y и r, если они корректны.
     * @throws IllegalArgumentException если данные не проходят валидацию или имеют неверный формат.
     */
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

    /**
     * Проверяет, попадает ли точка с заданными координатами в область.
     *
     * @param result объект Result, содержащий координаты x, y и радиус r.
     * @return true, если точка попадает в область, иначе false.
     */
    public boolean checkPoint(Result result) {
        return checkPoint(result.getX(), result.getY(), result.getR());
    }

    /**
     * Проверяет, попадает ли точка с заданными координатами в область, заданную радиусом R.
     *
     * @param x координата x точки.
     * @param y координата y точки.
     * @param r радиус R, определяющий область проверки.
     * @return true, если точка попадает в область, иначе false.
     */
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