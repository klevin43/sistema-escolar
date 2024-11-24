package com.kelvin.sistemaescolar.data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FragmentoBoletimRepository extends JpaRepository<FragmentoBoletim, Integer> {
    List<FragmentoBoletim> findByBoletimMatriculaAndBoletimAno(int matricula, int ano);
}
