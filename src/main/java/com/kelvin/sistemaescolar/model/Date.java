package com.kelvin.sistemaescolar.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Date {
    private int dia;
    private int mes;
    private int ano;
    
    public String toString() {
        return dia + "/" + mes + "/" + ano;
    }
    
    public String toUrl() {
        return ano + "-" + mes + "-" + dia;
    }
    
    public static Date fromUrl(String date) {
        String[] splitted = date.split("-");
        return new Date(
            Integer.valueOf(splitted[2]),
            Integer.valueOf(splitted[1]),
            Integer.valueOf(splitted[0])
        );
    }
}
