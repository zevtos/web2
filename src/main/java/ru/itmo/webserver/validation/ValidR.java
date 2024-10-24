package ru.itmo.webserver.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Аннотация для валидации значения радиуса R.
 * <p>
 * Ограничивает возможные значения радиуса R, которые должны быть одними из следующих: 1, 1.5, 2, 2.5, 3.
 * Используется для проверки корректности введенного пользователем значения радиуса.
 */
@Documented
@Constraint(validatedBy = ValidRValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface ValidR {
    /**
     * Сообщение об ошибке, которое будет отображено в случае некорректного значения R.
     *
     * @return сообщение об ошибке
     */
    String message() default "Invalid value for r. Must be one of 1, 1.5, 2, 2.5, 3.";

    /**
     * Группы валидации, к которым принадлежит данная аннотация.
     *
     * @return массив групп валидации
     */
    Class<?>[] groups() default {};

    /**
     * Дополнительная информация о полезной нагрузке (payload).
     *
     * @return массив полезной нагрузки
     */
    Class<? extends Payload>[] payload() default {};
}
