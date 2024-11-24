package com.kelvin.sistemaescolar.service;

import com.kelvin.sistemaescolar.data.Boletim;
import com.kelvin.sistemaescolar.data.FragmentoBoletim;
import com.kelvin.sistemaescolar.data.FragmentoBoletimRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FragmentoBoletimService {
    @Autowired
    FragmentoBoletimRepository fragmentoRepository;
    
    public FragmentoBoletim criarFragmentoBoletim(FragmentoBoletim fragmento) {
        fragmento.setId(null);
        fragmentoRepository.save(fragmento);
        return fragmento;
    }
    
    public FragmentoBoletim atualizarFragmentoBoletim(Integer id, FragmentoBoletim fragmentoAtualizado) {
        FragmentoBoletim fragmento = getFragmentoBoletim(id);
        if(fragmento == null) {
            return null;
        }
        fragmento.setDisciplinaNome(fragmentoAtualizado.getDisciplinaNome());
        fragmento.setNota1(fragmentoAtualizado.getNota1());
        fragmento.setNota2(fragmentoAtualizado.getNota2());
        fragmento.setNota3(fragmentoAtualizado.getNota3());
        fragmento.setNota4(fragmentoAtualizado.getNota4());
        fragmentoRepository.save(fragmento);
        return fragmento;
    }
    
    public FragmentoBoletim getFragmentoBoletim(Integer id) {
        return fragmentoRepository.findById(id).orElse(null);
    }
    
    public List<FragmentoBoletim> getFragmentosBoletim(Boletim boletim) {
        return fragmentoRepository.findByBoletimMatriculaAndBoletimAno(boletim.getMatricula(), boletim.getAno());
    }
}
