package com.kelvin.sistemaescolar.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {
    private String nome;
    private Date dataNascimento;
    private Integer matricula;
    private Integer anoEscolaridade;
    private String turno;
    private Boletim boletim;
}
