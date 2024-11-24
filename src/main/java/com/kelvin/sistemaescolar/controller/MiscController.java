package com.kelvin.sistemaescolar.controller;

import com.kelvin.sistemaescolar.Sistema;
import com.kelvin.sistemaescolar.model.AcessarAlunoObject;
import com.kelvin.sistemaescolar.data.Aluno;
import com.kelvin.sistemaescolar.data.Boletim;
import com.kelvin.sistemaescolar.data.FragmentoBoletim;
import com.kelvin.sistemaescolar.model.EditarNotaObject;
import com.kelvin.sistemaescolar.model.RegistrarAlunoObject;
import com.kelvin.sistemaescolar.service.AlunoService;
import com.kelvin.sistemaescolar.service.BoletimService;
import com.kelvin.sistemaescolar.service.FragmentoBoletimService;
import java.sql.Date;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MiscController {
    @Autowired
    private AlunoService alunoService;
    @Autowired
    private BoletimService boletimService;
    @Autowired
    private FragmentoBoletimService fragmentoService;
    
    @GetMapping("/")
    public String inicio() {
        return "index";
    }
    
    @GetMapping("/registrar_aluno")
    public String registrarAluno(Model model) {
        model.addAttribute("object", new RegistrarAlunoObject());
        model.addAttribute("inputInvalido", false);
        return "registrar_aluno";
    }
    
    @PostMapping("/registrar_aluno")
    public String registrarAlunoPost(Model model, @ModelAttribute RegistrarAlunoObject object) {
        if(object.getNome().isBlank()) {
            model.addAttribute("object", object);
            model.addAttribute("inputInvalido", true);
            return "registrar_aluno";
        }
        
        Aluno aluno = new Aluno();
        aluno.setNome(object.getNome());
        aluno.setDataNascimento(Date.valueOf(object.getData()));
        aluno.setAnoEscolaridade(object.getAnoEscolaridade());
        aluno.setTurno(object.getTurno());
        alunoService.criarAluno(aluno);
        
        Boletim boletim = new Boletim();
        boletim.setAno(2024);
        boletim.setMatricula(aluno.getMatricula());
        boletimService.criarBoletim(boletim);
        for(String disciplina : Sistema.getInstancia().getDisciplinas()) {
            FragmentoBoletim fragmento = new FragmentoBoletim();
            fragmento.setBoletim(boletim);
            fragmento.setDisciplinaNome(disciplina);
            fragmentoService.criarFragmentoBoletim(fragmento);
        }
        return "redirect:/dados?matricula=" + aluno.getMatricula() + "&ano=" + boletim.getAno();
    }
    
    @GetMapping("/acessar_aluno")
    public String acessarAluno(Model model, @RequestParam String location) {
        if(location == null || (!location.equals("dados") && !location.equals("boletim"))) {
            return "redirect:/";
        }
        model.addAttribute("location", location);
        model.addAttribute("object", new AcessarAlunoObject());
        model.addAttribute("inputInvalido", false);
        return "acessar_aluno";
    }
    
    @PostMapping("/acessar_aluno")
    public String acessarAlunoPost(Model model, @RequestParam String location, @ModelAttribute AcessarAlunoObject object) {
        if(location == null) {
            return "redirect:/";
        }
        Aluno aluno = alunoService.getAluno(object.getMatricula());
        if(aluno == null || !aluno.getDataNascimento().equals(Date.valueOf(object.getData()))) {
            model.addAttribute("location", location);
            model.addAttribute("object", object);
            model.addAttribute("inputInvalido", true);
            return "acessar_aluno";
        }
        return "redirect:/" + location +
            "?matricula=" + aluno.getMatricula() +
            "&dataNascimento=" + object.getData() +
            "&ano=" + object.getAno();
    }
    
    @GetMapping("/dados")
    public String dados(Model model, @RequestParam Integer matricula, @RequestParam Integer ano) {
        if(matricula == null) {
            return "redirect:/";
        }
        Aluno aluno = alunoService.getAluno(matricula);
        if(aluno == null) {
            return "redirect:/";
        }
        model.addAttribute("aluno", aluno);
        model.addAttribute("dataNascimento", aluno.getDataNascimento().toString());
        model.addAttribute("ano", ano);
        return "dados_escolares";
    }
    
    @GetMapping("/boletim")
    public String boletim(Model model, @RequestParam Integer matricula, @RequestParam Integer ano) {
        if(matricula == null) {
            return "redirect:/";
        }
        Boletim boletim = boletimService.getBoletim(matricula, ano);
        if(boletim == null) {
            return "redirect:/";
        }
        Aluno aluno = alunoService.getAluno(matricula);
        if(aluno == null) {
            return "redirect:/";
        }
        model.addAttribute("media", Sistema.getInstancia().getMedia());
        model.addAttribute("quantBim", Sistema.QUANTIDADE_BIMESTRES);
        model.addAttribute("aluno", aluno);
        model.addAttribute("boletim", boletim);
        model.addAttribute("disciplinas", fragmentoService.getFragmentosBoletim(boletim));
        model.addAttribute("formatString", "%.2f");
        return "boletim";
    }
    
    @GetMapping("/editar_nota")
    public String editarNota(Model model, @RequestParam Integer matricula, @RequestParam Integer ano) {
        if(matricula == null || ano == null) {
            return "redirect:/";
        }
        Boletim boletim = boletimService.getBoletim(matricula, ano);
        if(boletim == null) {
            return "redirect:/";
        }
        Aluno aluno = alunoService.getAluno(matricula);
        if(aluno == null) {
            return "redirect:/";
        }
        model.addAttribute("editarNotaObject", new EditarNotaObject());
        model.addAttribute("aluno", aluno);
        model.addAttribute("disciplinas", Sistema.getInstancia().getDisciplinas());
        model.addAttribute("bimestres", IntStream.range(1, Sistema.QUANTIDADE_BIMESTRES + 1).toArray());
        model.addAttribute("matricula", matricula);
        model.addAttribute("ano", ano);
        model.addAttribute("notaMaxima", Sistema.getInstancia().getNotaMaxima());
        return "editar_nota";
    }
    
    @PostMapping("/editar_nota")
    public String editarNotaPost(@ModelAttribute EditarNotaObject editarNotaObject, @RequestParam Integer matricula, @RequestParam Integer ano) {
        if(matricula == null || ano == null) {
            return "redirect:/";
        }
        Boletim boletim = boletimService.getBoletim(matricula, ano);
        if(boletim == null) {
            return "redirect:/";
        }
        Aluno aluno = alunoService.getAluno(matricula);
        if(aluno == null) {
            return "redirect:/";
        }
        editarNota(boletim, editarNotaObject.getDisciplina(), Integer.valueOf(editarNotaObject.getBimestre()), editarNotaObject.getNota());
        return "redirect:/boletim?matricula=" + matricula +
            "&dataNascimento=" + aluno.getDataNascimento().toString() +
            "&ano=" + ano;
    }
    
    private boolean editarNota(Boletim boletim, String disciplina, int bim, float nota) {
        if(bim <= 0) {
            return false;
        }
        if(nota < 0 || nota > Sistema.getInstancia().getNotaMaxima()) {
            return false;
        }
        List<FragmentoBoletim> disciplinas = fragmentoService.getFragmentosBoletim(boletim);
        for(FragmentoBoletim fragmento : disciplinas) {
            if(!fragmento.getDisciplinaNome().equals(disciplina)) {
                continue;
            }
            switch(bim) {
                case 1 -> fragmento.setNota1(nota);
                case 2 -> fragmento.setNota2(nota);
                case 3 -> fragmento.setNota3(nota);
                case 4 -> fragmento.setNota4(nota);
            }
            fragmentoService.atualizarFragmentoBoletim(fragmento.getId(), fragmento);
            break;
        }
        return true;
    }
}
