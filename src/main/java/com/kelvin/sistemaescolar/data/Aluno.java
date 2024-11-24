package com.kelvin.sistemaescolar.data;

import com.kelvin.sistemaescolar.service.BoletimService;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Entity
@Table(name="alunos")
@NoArgsConstructor
public class Aluno {
    @Autowired
    private static BoletimService boletimService;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer matricula;
    private String nome;
    private Date dataNascimento;
    private Integer anoEscolaridade;
    private String turno;
    
    public Boletim getBoletim(int ano) {
        return boletimService.getBoletim(matricula, ano);
    }
}
