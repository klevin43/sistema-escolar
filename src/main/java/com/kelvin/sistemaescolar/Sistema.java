package com.kelvin.sistemaescolar;

import java.util.List;
import lombok.Data;

@Data
public class Sistema {
    private static Sistema instancia = null;
    
    private int quantBimestres;
    private float notaMaxima;
    private float media;
    private List<String> disciplinas;
    
    public float pontosTotaisParaMedia() {
        return media * quantBimestres;
    }
    
    public static Sistema getInstancia() {
        if(instancia == null) {
            instancia = new Sistema();
        }
        return instancia;
    }
}
