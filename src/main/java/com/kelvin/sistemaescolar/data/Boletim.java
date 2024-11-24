package com.kelvin.sistemaescolar.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name="boletins")
@IdClass(BoletimId.class)
@AllArgsConstructor
@NoArgsConstructor
public class Boletim {
    @Id
    private Integer matricula;
    
    @Id
    private Integer ano;
}
