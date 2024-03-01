package com.example.SpringREST.models;

import jakarta.persistence.*;
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
@Entity
@Table(name = "indication")
public class Indication {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "value")
    @Min(value = -100,message = "Показатель температуры не может быть ниже -100 градусов.")
    @Max(value = 100,message = "Показатель температуры не может быть больше 100 градусов.")
    private double value;

    @Column(name = "isRaining")
    private boolean raining;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "measurer_id", referencedColumnName = "id")
    private Measurer measurer;

}
