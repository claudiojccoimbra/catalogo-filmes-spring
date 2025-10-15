package com.claudiojccoimbra.catalogo.controller;
import com.claudiojccoimbra.catalogo.domain.Estudio;
import com.claudiojccoimbra.catalogo.service.EstudioService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/estudios")
public class EstudioController {
    private final EstudioService service;
    public EstudioController(EstudioService service){ this.service = service; }

    @PostMapping public Estudio criar(@RequestBody Estudio e){ return service.incluir(e); }
    @GetMapping public List<Estudio> listar(){ return service.listarTodos(); }
    @GetMapping("/{id}") public Estudio obter(@PathVariable Long id){ return service.buscarPorId(id); }
    @PutMapping("/{id}") public Estudio atualizar(@PathVariable Long id, @RequestBody Estudio e){ return service.alterar(id,e); }
    @DeleteMapping("/{id}") public void remover(@PathVariable Long id){ service.excluir(id); }
}