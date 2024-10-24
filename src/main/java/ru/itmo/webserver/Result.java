package ru.itmo.webserver;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmo.webserver.validation.ValidR;
import ru.itmo.webserver.validation.ValidX;

import java.beans.JavaBean;
import java.io.Serializable;

/**
 * Result - класс, представляющий результат проверки попадания точки в область.
 * <p>
 * Содержит данные о координатах точки (x, y), радиусе (r), идентификаторе результата,
 * а также информации о попадании точки в область.
 */
//@Entity
//@Table(name = "results")
@JavaBean
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {

    /**
     * Уникальный идентификатор результата (если сохраняется в базу данных).
     */
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@NotNull
    private Long id;

    /**
     * Координата X точки, проверяемая на попадание в область.
     * Значение должно удовлетворять валидации, заданной аннотацией @ValidX.
     */
    @ValidX
    private double x;

    /**
     * Координата Y точки, проверяемая на попадание в область.
     * Допустимое значение должно находиться в диапазоне от -5 до 5.
     */
    @Min(-5)
    @Max(5)
    private double y;

    /**
     * Радиус R, определяющий размер области проверки.
     * Значение должно удовлетворять валидации, заданной аннотацией @ValidR.
     */
    @ValidR
    private double r;

    /**
     * Флаг, показывающий, попала ли точка в заданную область.
     */
    private boolean isHit;
}
