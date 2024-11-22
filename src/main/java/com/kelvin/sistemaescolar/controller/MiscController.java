package com.kelvin.sistemaescolar.controller;

import com.kelvin.sistemaescolar.Sistema;
import com.kelvin.sistemaescolar.model.Aluno;
import com.kelvin.sistemaescolar.model.Boletim;
import com.kelvin.sistemaescolar.model.Boletim.FragmentoBoletim;
import com.kelvin.sistemaescolar.model.Date;
import com.kelvin.sistemaescolar.model.EditarNotaObject;
import com.kelvin.sistemaescolar.model.RegistrarAlunoObject;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MiscController {
    Map<Integer, Aluno> alunos = new HashMap<>(Map.of(
        123456789, new Aluno("João Donalds", new Date(30, 4, 1925), 123456789, 2, "Manhã",
            new Boletim(2024, List.of(
                new FragmentoBoletim("Português", new ArrayList<>(List.of(5f, 5f, 4.5f))),
                new FragmentoBoletim("Geografia", new ArrayList<>(List.of(7f, 2.5f, 5.5f))),
                new FragmentoBoletim("Matemática", new ArrayList<>(List.of(9.5f, 1.5f, 9f))),
                new FragmentoBoletim("Física", new ArrayList<>(List.of(7f, 7f, 7f))),
                new FragmentoBoletim("Química", new ArrayList<>(List.of(1f, 9f, 10f))),
                new FragmentoBoletim("História", new ArrayList<>(List.of(10f, 9f, 9f))),
                new FragmentoBoletim("Biologia", new ArrayList<>(List.of(10f, 10f, 10f)))
            ))
        )
    ));
    
    @GetMapping("/")
    public String inicio() {
        return "index";
    }
    
    @GetMapping("/registrar_aluno")
    public String registrarAluno(Model model) {
        model.addAttribute("object", new RegistrarAlunoObject());
        return "registrar_aluno";
    }
    
    @PostMapping("/registrar_aluno")
    public String registrarAlunoPost(@ModelAttribute RegistrarAlunoObject object, @ModelAttribute String data) {
        Aluno aluno = new Aluno();
        aluno.setNome(object.getNome());
        aluno.setDataNascimento(Date.fromUrl(object.getData()));
        aluno.setMatricula(object.getMatricula());
        aluno.setAnoEscolaridade(object.getAnoEscolaridade());
        aluno.setTurno(object.getTurno());
        List<FragmentoBoletim> fragmentos = new ArrayList<>();
        for(String disciplina : Sistema.getInstancia().getDisciplinas()) {
            fragmentos.add(new FragmentoBoletim(disciplina, new ArrayList<>()));
        }
        aluno.setBoletim(new Boletim(2024, fragmentos));
        alunos.put(aluno.getMatricula(), aluno);
        return "redirect:/dados?matricula=" + aluno.getMatricula() + "&ano=" + aluno.getBoletim().getAno();
    }
    
    @GetMapping("/acessar_aluno")
    public String acessarAluno(Model model, @RequestParam String location) {
        if(location == null) {
            return "redirect:/";
        }
        if(location.equals("dados") || location.equals("boletim")) {
            model.addAttribute("location", location);
            return "acessar_aluno";
        }
        return "redirect:/";
    }
    
    @GetMapping("/dados")
    public String dados(Model model, @RequestParam Integer matricula, @RequestParam Integer ano) {
        if(matricula == null) {
            return "redirect:/";
        }
        Aluno aluno = alunos.getOrDefault(matricula, null);
        if(aluno == null) {
            return "redirect:/";
        }
        model.addAttribute("aluno", aluno);
        model.addAttribute("dataNascimento", aluno.getDataNascimento().toString());
        model.addAttribute("ano", ano);
        return "dados_escolares";
    }
    
    @GetMapping("/boletim")
    public String boletim(Model model, @RequestParam Integer matricula) {
        if(matricula == null) {
            return "redirect:/";
        }
        Aluno aluno = alunos.getOrDefault(matricula, null);
        if(aluno == null) {
            return "redirect:/";
        }
        model.addAttribute("media", Sistema.getInstancia().getMedia());
        model.addAttribute("quantBim", Sistema.getInstancia().getQuantBimestres());
        model.addAttribute("aluno", aluno);
        model.addAttribute("boletim", aluno.getBoletim());
        return "boletim";
    }
    
    @GetMapping("/editar_nota")
    public String editarNota(Model model, @RequestParam Integer matricula, @RequestParam Integer ano) {
        if(matricula == null || ano == null) {
            return "redirect:/";
        }
        Aluno aluno = alunos.getOrDefault(matricula, null);
        if(aluno == null) {
            return "redirect:/";
        }
        model.addAttribute("editarNotaObject", new EditarNotaObject());
        model.addAttribute("aluno", aluno);
        model.addAttribute("disciplinas", Sistema.getInstancia().getDisciplinas());
        model.addAttribute("bimestres", IntStream.range(1, Sistema.getInstancia().getQuantBimestres() + 1).toArray());
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
        Aluno aluno = alunos.getOrDefault(matricula, null);
        if(aluno == null) {
            return "redirect:/";
        }
        aluno.getBoletim().editarNota(editarNotaObject.getDisciplina(), Integer.valueOf(editarNotaObject.getBimestre()), editarNotaObject.getNota());
        return "redirect:/boletim?matricula=" + aluno.getMatricula() +
            "&dataNascimento=" + aluno.getDataNascimento().toUrl() +
            "&ano=" + ano;
    }
}
