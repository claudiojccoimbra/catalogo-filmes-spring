package com.claudiojccoimbra.catalogo.controller;

import com.claudiojccoimbra.catalogo.domain.Serie;
import com.claudiojccoimbra.catalogo.service.SerieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/series")
public class SerieController {
    private final SerieService service;
    public SerieController(SerieService service){ this.service = service; }

    @PostMapping
    public ResponseEntity<Serie> criar(@RequestBody Serie s){
        Serie salvo = service.incluir(s);
        return ResponseEntity.created(URI.create("/api/v1/series/" + salvo.getId())).body(salvo);
    }
    @GetMapping
    public ResponseEntity<List<Serie>> listar(){ return ResponseEntity.ok(service.listarTodos()); }
    @GetMapping("/{id}")
    public ResponseEntity<Serie> obter(@PathVariable Long id){
        return service.buscarPorId(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Serie> atualizar(@PathVariable Long id, @RequestBody Serie s){
        return ResponseEntity.ok(service.alterar(id,s));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){ service.excluir(id); return ResponseEntity.noContent().build(); }
}
