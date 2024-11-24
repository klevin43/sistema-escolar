package com.kelvin.sistemaescolar.service;

import com.kelvin.sistemaescolar.data.Aluno;
import com.kelvin.sistemaescolar.data.AlunoRepository;
import com.kelvin.sistemaescolar.data.Boletim;
import com.kelvin.sistemaescolar.data.BoletimId;
import com.kelvin.sistemaescolar.data.BoletimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoletimService {
    @Autowired
    BoletimRepository boletimRepository;
    @Autowired
    AlunoRepository alunoRepository;
    
    public Boletim criarBoletim(Boletim boletim) {
        boletimRepository.save(boletim);
        return boletim;
    }
    
    public Boletim getBoletim(Integer matricula, Integer ano) {
        /* Aluno aluno = alunoRepository.findById(matricula).orElse(null);
        if(aluno == null) {
            return null;
        } */
        return boletimRepository.findById(new BoletimId(matricula, ano)).orElse(null);
    }
}
