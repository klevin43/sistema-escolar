package com.kelvin.sistemaescolar.model;

import com.kelvin.sistemaescolar.Sistema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Boletim {
    @Data
    @AllArgsConstructor
    public static class FragmentoBoletim {
        private String disciplinaNome;
        private List<Float> notas;
        
        public Float getNota(int bim) {
            try {
                return notas.get(bim - 1);
            } catch(IndexOutOfBoundsException e) {
                return null;
            }
        }
        
        public String getNotaAsString(int bim) {
            Float nota = getNota(bim);
            return nota != null ? String.format("%.2f", nota) : "";
        }
        
        public boolean isNotaNaMedia(int bim) {
            Float nota = getNota(bim);
            if(nota == null) {
                return false;
            }
            return nota >= Sistema.getInstancia().getMedia();
        }
        
        public float notaAcumulada() {
            return notas.stream().reduce(0f, Float::sum);
        }
        
        public boolean isNotaAcumuladaNaMedia() {
            return notaAcumulada() >= (Sistema.getInstancia().getMedia() * Sistema.getInstancia().getQuantBimestres());
        }
        
        public String notaAcumuladaAsString() {
            return String.format("%.2f", notaAcumulada());
        }
        
        public float media() {
            return notas.size() > 0 ? notaAcumulada() / notas.size() : 0f;
        }
        
        public boolean taNaMedia() {
            return media() >= Sistema.getInstancia().getMedia();
        }
        
        public String getMediaAsString() {
            return String.format("%.2f", media());
        }
    }
    
    private int ano;
    private List<FragmentoBoletim> disciplinas;
    
    public boolean editarNota(String disciplina, int bim, float nota) {
        if(bim <= 0) {
            return false;
        }
        if(nota < 0 || nota > Sistema.getInstancia().getNotaMaxima()) {
            return false;
        }
        for(FragmentoBoletim fragmento : disciplinas) {
            if(!fragmento.getDisciplinaNome().equals(disciplina)) {
                continue;
            }
            if(bim > fragmento.getNotas().size()) {
                fragmento.getNotas().add(nota);
                break;
            }
            fragmento.getNotas().set(bim - 1, nota);
            break;
        }
        return true;
    }
}
