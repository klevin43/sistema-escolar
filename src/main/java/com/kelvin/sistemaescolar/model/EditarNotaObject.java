package com.kelvin.sistemaescolar.model;

import lombok.Data;

@Data
public class EditarNotaObject {
    private String disciplina;
    private String bimestre;
    private float nota;
}
