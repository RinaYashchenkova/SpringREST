package com.example.SpringREST.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasurerDTO {
    @Column(name = "measurer_name", unique = true)
    @Size(min = 3,max = 30, message = "Название сенсора должно быть не менее 3 и не более 30 символов.")
    @NotEmpty(message = "Название сенсора не дожлно быть пустым.")
    private String measurerName;
}
