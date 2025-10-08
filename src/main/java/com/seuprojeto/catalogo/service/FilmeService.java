package com.seuprojeto.catalogo.service;

import com.seuprojeto.catalogo.domain.Filme;
import com.seuprojeto.catalogo.repository.FilmeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FilmeService {

    private final FilmeRepository repo;

    public FilmeService(FilmeRepository repo){
        this.repo = repo;
    }

    public Filme criar(Filme f){
        if(f.getTitulo()==null || f.getTitulo().isBlank())
            throw new IllegalArgumentException("Título é obrigatório");
        return repo.save(f);
    }

    public List<Filme> listar(){ return repo.findAll(); }

    public Filme obter(Long id){
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Filme não encontrado"));
    }

    public Filme atualizar(Long id, Filme f){
        Filme atual = obter(id);
        atual.setTitulo(f.getTitulo());
        atual.setAno(f.getAno());
        atual.setDuracaoMin(f.getDuracaoMin());
        return repo.save(atual);
    }

    public void remover(Long id){ repo.deleteById(id); }
}
