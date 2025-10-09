package com.claudiojccoimbra.catalogo.service;

import com.claudiojccoimbra.catalogo.domain.Filme;
import com.claudiojccoimbra.catalogo.repository.FilmeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FilmeService implements CrudService<Filme, Long> {

    private final FilmeRepository repo;

    public FilmeService(FilmeRepository repo){ this.repo = repo; }

    private void validar(Filme f){
        if(f.getTitulo()==null || f.getTitulo().isBlank())
            throw new IllegalArgumentException("Título é obrigatório");
    }

    // Interface genérica
    @Override public Filme salvar(Filme f){ validar(f); return repo.save(f); }
    @Override public Optional<Filme> buscarPorId(Long id){ return repo.findById(id); }
    @Override public List<Filme> listarTodos(){ return repo.findAll(); }
    @Override public void excluir(Long id){ repo.deleteById(id); }

    // Mantém os métodos já usados nos controllers
    public Filme criar(Filme f){ return salvar(f); }
    public List<Filme> listar(){ return listarTodos(); }
    public Filme obter(Long id){ return buscarPorId(id).orElseThrow(() -> new NoSuchElementException("Filme não encontrado")); }
    public Filme atualizar(Long id, Filme f){
        Filme atual = obter(id);
        atual.setTitulo(f.getTitulo());
        atual.setAno(f.getAno());
        atual.setDuracaoMin(f.getDuracaoMin());
        return salvar(atual);
    }
    public void remover(Long id){ excluir(id); }
}
