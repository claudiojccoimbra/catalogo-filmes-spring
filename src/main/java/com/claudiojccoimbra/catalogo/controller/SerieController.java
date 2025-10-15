package com.claudiojccoimbra.catalogo.controller;
import com.claudiojccoimbra.catalogo.domain.Serie;
import com.claudiojccoimbra.catalogo.service.SerieService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/series")
public class SerieController {
    private final SerieService service;
    public SerieController(SerieService service){ this.service = service; }

    @PostMapping public Serie criar(@RequestBody Serie s){ return service.incluir(s); }
    @GetMapping public List<Serie> listar(){ return service.listarTodos(); }
    @GetMapping("/{id}") public Serie obter(@PathVariable Long id){ return service.buscarPorId(id).orElse(null); }
    @PutMapping("/{id}") public Serie atualizar(@PathVariable Long id, @RequestBody Serie s){ return service.alterar(id,s); }
    @DeleteMapping("/{id}") public void remover(@PathVariable Long id){ service.excluir(id); }
}