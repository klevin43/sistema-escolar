package com.kelvin.sistemaescolar.data;

import com.kelvin.sistemaescolar.Sistema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name="fragmentos_boletim")
@AllArgsConstructor
@NoArgsConstructor
public class FragmentoBoletim {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private Boletim boletim;
    private String disciplinaNome;
    private Float nota1;
    private Float nota2;
    private Float nota3;
    private Float nota4;

    private static boolean isNotaNaMedia(Float nota) {
        if(nota == null) {
            return false;
        }
        return nota >= Sistema.getInstancia().getMedia();
    }
    
    public boolean isNota1NaMedia() {
        return isNotaNaMedia(nota1);
    }
    
    public boolean isNota2NaMedia() {
        return isNotaNaMedia(nota2);
    }
    
    public boolean isNota3NaMedia() {
        return isNotaNaMedia(nota3);
    }
    
    public boolean isNota4NaMedia() {
        return isNotaNaMedia(nota4);
    }

    public float notaAcumulada() {
        return (nota1 == null ? 0f : nota1) +
            (nota2 == null ? 0f : nota2) +
            (nota3 == null ? 0f : nota3) +
            (nota4 == null ? 0f : nota4);
    }
    
    public int quantidadeDeNotas() {
        int quant = 4;
        if(nota1 == null) quant--;
        if(nota2 == null) quant--;
        if(nota3 == null) quant--;
        if(nota4 == null) quant--;
        return quant;
    }

    public boolean isNotaAcumuladaNaMedia() {
        return notaAcumulada() >= Sistema.getInstancia().pontosTotaisParaMedia();
    }

    public float getPontosRestantesParaMedia() {
        return isNotaAcumuladaNaMedia() ? 0 : Sistema.getInstancia().pontosTotaisParaMedia() - notaAcumulada();
    }

    public float getPontosRestantesParaCadaBimestre() {
        int quantBimestresFaltando = Sistema.QUANTIDADE_BIMESTRES - quantidadeDeNotas();
        if(quantBimestresFaltando == 0) {
            return 0f;
        }
        return getPontosRestantesParaMedia() / quantBimestresFaltando;
    }

    public float getMedia() {
        return quantidadeDeNotas() == 0 ? 0f : notaAcumulada() / quantidadeDeNotas();
    }

    public boolean taNaMedia() {
        return getMedia() >= Sistema.getInstancia().getMedia();
    }
}