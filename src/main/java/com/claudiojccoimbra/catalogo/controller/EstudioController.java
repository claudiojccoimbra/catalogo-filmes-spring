package com.claudiojccoimbra.catalogo.controller;

import com.claudiojccoimbra.catalogo.domain.Estudio;
import com.claudiojccoimbra.catalogo.service.EstudioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/estudios")
public class EstudioController {
    private final EstudioService service;
    public EstudioController(EstudioService service){ this.service = service; }

    @PostMapping
    public ResponseEntity<Estudio> criar(@RequestBody Estudio e){
        Estudio salvo = service.incluir(e);
        return ResponseEntity.created(URI.create("/api/v1/estudios/" + salvo.getId())).body(salvo);
    }
    @GetMapping
    public ResponseEntity<List<Estudio>> listar(){ return ResponseEntity.ok(service.listarTodos()); }
    @GetMapping("/{id}")
    public ResponseEntity<Estudio> obter(@PathVariable Long id){
        try { return ResponseEntity.ok(service.buscarPorId(id)); }
        catch (Exception ex){ return ResponseEntity.notFound().build(); }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Estudio> atualizar(@PathVariable Long id, @RequestBody Estudio e){
        return ResponseEntity.ok(service.alterar(id,e));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){ service.excluir(id); return ResponseEntity.noContent().build(); }
}
