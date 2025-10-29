package com.claudiojccoimbra.catalogo.controller;

import com.claudiojccoimbra.catalogo.controller.dto.AvaliacaoRequest;
import com.claudiojccoimbra.catalogo.domain.Avaliacao;
import com.claudiojccoimbra.catalogo.service.AvaliacaoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService service;

    public AvaliacaoController(AvaliacaoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Avaliacao criar(@Valid @RequestBody AvaliacaoRequest req) {
        return service.incluir(req);
    }

    @GetMapping("/filme/{filmeId}")
    public List<Avaliacao> listarPorFilme(@PathVariable Long filmeId) {
        return service.listarPorFilme(filmeId);
    }

    @GetMapping("/buscar-por-titulo-e-nota")
    public List<Avaliacao> buscarPorTituloENota(@RequestParam String titulo,  @RequestParam @Min(0) int notaMin) {
      return service.buscarPorTituloENota(titulo, notaMin);
    }

    @GetMapping("/buscar-por-dominio-email")
    public List<Avaliacao> buscarPorDominioEmail(@RequestParam String dominio) {
        return service.buscarPorDominioEmail(dominio);
    }
    
}
