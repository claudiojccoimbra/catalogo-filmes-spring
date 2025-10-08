package com.seuprojeto.catalogo.controller;

import com.seuprojeto.catalogo.domain.Filme;
import com.seuprojeto.catalogo.service.FilmeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/filmes")
public class FilmeController {

    private final FilmeService service;

    public FilmeController(FilmeService service){
        this.service = service;
    }

    @PostMapping
    public Filme criar(@RequestBody Filme f){ return service.criar(f); }

    @GetMapping
    public List<Filme> listar(){ return service.listar(); }

    @GetMapping("/{id}")
    public Filme obter(@PathVariable Long id){ return service.obter(id); }

    @PutMapping("/{id}")
    public Filme atualizar(@PathVariable Long id, @RequestBody Filme f){ return service.atualizar(id,f); }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id){ service.remover(id); }
}
