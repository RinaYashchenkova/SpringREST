package com.example.SpringREST.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "measurer")
public class Measurer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "measurer_name", unique = true)
    @Size(min = 3,max = 30, message = "Название сенсора должно быть не менее 3 и не более 30 символов.")
    @NotEmpty(message = "Название сенсора не дожлно быть пустым.")
    private String measurerName;

    @OneToMany(mappedBy = "measurer", cascade = CascadeType.REMOVE)
    List<Indication> indications;
}
