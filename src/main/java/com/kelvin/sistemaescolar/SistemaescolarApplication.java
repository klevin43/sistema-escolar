package com.kelvin.sistemaescolar;

import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SistemaescolarApplication {

	public static void main(String[] args) {
        Sistema sistema = Sistema.getInstancia();
        sistema.setQuantBimestres(4);
        sistema.setMedia(5f);
        sistema.setNotaMaxima(10f);
        sistema.setDisciplinas(List.of(
            "Português",
            "Geografia",
            "Matemática",
            "Física",
            "Química",
            "História",
            "Biologia"
        ));
		SpringApplication.run(SistemaescolarApplication.class, args);
	}

}
