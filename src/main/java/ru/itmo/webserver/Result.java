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

//@Entity
//@Table(name = "results")
@JavaBean
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @NotNull
    private Long id;

    @ValidX
    private double x;

    @Min(-5)
    @Max(5)
    private double y;

    @ValidR
    private double r;

    private boolean isHit;
}
