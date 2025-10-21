package com.claudiojccoimbra.catalogo.controller;

import com.claudiojccoimbra.catalogo.domain.Filme;
import com.claudiojccoimbra.catalogo.service.FilmeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/filmes")
public class FilmeController {
    private final FilmeService service;
    public FilmeController(FilmeService service){ this.service = service; }

    @PostMapping
    public ResponseEntity<Filme> criar(@RequestBody Filme f){
        Filme salvo = service.incluir(f);
        return ResponseEntity.created(URI.create("/api/v1/filmes/" + salvo.getId())).body(salvo);
    }
    @GetMapping
    public ResponseEntity<List<Filme>> listar(){ return ResponseEntity.ok(service.listarTodos()); }
    @GetMapping("/{id}")
    public ResponseEntity<Filme> obter(@PathVariable Long id){
        return service.buscarPorId(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Filme> atualizar(@PathVariable Long id, @RequestBody Filme f){
        return ResponseEntity.ok(service.alterar(id,f));
    }
    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Filme> inativar(@PathVariable Long id){ return ResponseEntity.ok(service.inativar(id)); }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){ service.excluir(id); return ResponseEntity.noContent().build(); }
}
