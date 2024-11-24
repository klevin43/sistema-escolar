package com.kelvin.sistemaescolar.model;

import lombok.Data;

@Data
public class RegistrarAlunoObject {
    private String nome;
    private String data;
    private Integer anoEscolaridade;
    private String turno;
}
