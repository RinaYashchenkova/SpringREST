package com.example.SpringREST.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndicationDTO {
    @Min(value = -100,message = "Показатель температуры не может быть ниже -100 градусов.")
    @Max(value = 100,message = "Показатель температуры не может быть больше 100 градусов.")
    private double value;

    private boolean raining;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime createdAt;
}
