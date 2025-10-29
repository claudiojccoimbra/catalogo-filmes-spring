package com.claudiojccoimbra.catalogo.service;

import com.claudiojccoimbra.catalogo.controller.dto.AvaliacaoRequest;
import com.claudiojccoimbra.catalogo.domain.Avaliacao;
import com.claudiojccoimbra.catalogo.domain.Filme;
import com.claudiojccoimbra.catalogo.repository.AvaliacaoRepository;
import com.claudiojccoimbra.catalogo.repository.FilmeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AvaliacaoService {
    private final AvaliacaoRepository repo;
    private final FilmeRepository filmes;

    public AvaliacaoService(AvaliacaoRepository repo, FilmeRepository filmes) {
        this.repo = repo;
        this.filmes = filmes;
    }

    @Transactional
    public Avaliacao incluir(AvaliacaoRequest req) {
        Filme filme = filmes.findById(req.filmeId())
                .orElseThrow(() -> new IllegalArgumentException("Filme não encontrado: " + req.filmeId()));
        return repo.save(new Avaliacao(req.autorEmail(), req.nota(), req.comentario(), req.data(), filme));
    }

    public List<Avaliacao> listarPorFilme(Long filmeId) {
        return repo.findByFilme_IdOrderByDataDesc(filmeId);
    }

    public List<Avaliacao> buscarPorTituloENota(String parteTitulo, int notaMin) {
        return repo.findByFilme_TituloContainingIgnoreCaseAndNotaGreaterThanEqual(parteTitulo, notaMin);
    }

    public List<Avaliacao> buscarPorDominioEmail(String dominio) {
        return repo.findByAutorEmailEndingWithIgnoreCase(dominio);
    }
}
