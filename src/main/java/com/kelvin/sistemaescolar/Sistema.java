package com.kelvin.sistemaescolar;

import java.util.List;
import lombok.Data;

@Data
public class Sistema {
    public static int QUANTIDADE_BIMESTRES = 4;
    
    private static Sistema instancia = null;
    
    private float notaMaxima;
    private float media;
    private List<String> disciplinas;
    
    public float pontosTotaisParaMedia() {
        return media * QUANTIDADE_BIMESTRES;
    }
    
    public static Sistema getInstancia() {
        if(instancia == null) {
            instancia = new Sistema();
        }
        return instancia;
    }
}
