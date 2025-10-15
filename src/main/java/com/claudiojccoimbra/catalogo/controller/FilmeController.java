package com.claudiojccoimbra.catalogo.controller;
import com.claudiojccoimbra.catalogo.domain.Filme;
import com.claudiojccoimbra.catalogo.service.FilmeService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/filmes")
public class FilmeController {
    private final FilmeService service;
    public FilmeController(FilmeService service){ this.service = service; }

    @PostMapping public Filme criar(@RequestBody Filme f){ return service.incluir(f); }
    @GetMapping public List<Filme> listar(){ return service.listarTodos(); }
    @GetMapping("/{id}") public Filme obter(@PathVariable Long id){ return service.buscarPorId(id).orElse(null); }
    @PutMapping("/{id}") public Filme atualizar(@PathVariable Long id, @RequestBody Filme f){ return service.alterar(id,f); }
    @PatchMapping("/{id}/inativar") public Filme inativar(@PathVariable Long id){ return service.inativar(id); }
    @DeleteMapping("/{id}") public void remover(@PathVariable Long id){ service.excluir(id); }
}