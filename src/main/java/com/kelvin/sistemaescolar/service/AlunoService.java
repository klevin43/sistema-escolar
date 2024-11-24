package com.kelvin.sistemaescolar.service;

import com.kelvin.sistemaescolar.data.AlunoRepository;
import com.kelvin.sistemaescolar.data.Aluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {
    @Autowired
    AlunoRepository alunoRepository;
    
    public Aluno criarAluno(Aluno aluno) {
        aluno.setMatricula(null);
        alunoRepository.save(aluno);
        return aluno;
    }
    
    public Aluno getAluno(Integer matricula) {
        return alunoRepository.findById(matricula).orElse(null);
    }
}
