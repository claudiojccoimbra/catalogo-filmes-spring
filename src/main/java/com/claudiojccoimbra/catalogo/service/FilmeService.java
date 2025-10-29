package com.claudiojccoimbra.catalogo.service;

import com.claudiojccoimbra.catalogo.domain.DetalhesFilme;
import com.claudiojccoimbra.catalogo.domain.Filme;
import com.claudiojccoimbra.catalogo.exception.FilmeInvalidoException;
import com.claudiojccoimbra.catalogo.exception.FilmeNaoEncontradoException;
import com.claudiojccoimbra.catalogo.repository.FilmeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class FilmeService implements CrudService<Filme, Long> {
    private final FilmeRepository repo;

    public FilmeService(FilmeRepository repo){ this.repo = repo; }

    private void validar(Filme f){
        if (f == null) throw new FilmeInvalidoException("Filme nulo");
        if (f.getTitulo() == null || f.getTitulo().isBlank())
            throw new FilmeInvalidoException("Título é obrigatório");
        if (f.getDuracaoMin() != null && f.getDuracaoMin() <= 0)
            throw new FilmeInvalidoException("Duração deve ser positiva");
    }

    @Override
    public Filme incluir(Filme f){
        validar(f);
        if (f.getId() != null)
            throw new FilmeInvalidoException("Não informe ID ao incluir");
        if (f.getDetalhes()!=null && f.getDetalhes().getId()!=null)
            throw new FilmeInvalidoException("DetalhesFilme novo não deve ter id");
        return repo.save(f); // cascade persiste DetalhesFilme
    }

    @Override
    public Filme alterar(Long id, Filme f){
        validar(f);
        Filme atual = buscarPorId(id).orElseThrow(() -> new FilmeNaoEncontradoException(id));

        atual.setTitulo(f.getTitulo());
        atual.setAno(f.getAno());
        atual.setDuracaoMin(f.getDuracaoMin());
        if (f.getAtivo()!=null) atual.setAtivo(f.getAtivo());

        // atualizar/remover detalhes respeitando orphanRemoval
        if (f.getDetalhes() == null) {
            atual.setDetalhes(null); // remove e orfana corretamente
        } else {
            DetalhesFilme d = atual.getDetalhes();
            if (d == null) {
                d = new DetalhesFilme();     // nova instância gerenciada
                atual.setDetalhes(d);
            }
            d.setSinopse(f.getDetalhes().getSinopse());
            d.setIdioma(f.getDetalhes().getIdioma());
            d.setClassificacao(f.getDetalhes().getClassificacao());
        }

        return atual;
    }

    public Filme inativar(Long id){
        Filme atual = buscarPorId(id).orElseThrow(() -> new FilmeNaoEncontradoException(id));
        atual.setAtivo(false);
        return atual; // dirty checking
    }

    @Override @Transactional(readOnly = true)
    public Optional<Filme> buscarPorId(Long id){ return repo.findById(id); }

    @Override @Transactional(readOnly = true)
    public List<Filme> listarTodos(){ return repo.findAll(); }

    @Override
    public void excluir(Long id){
        if (!repo.existsById(id)) throw new FilmeNaoEncontradoException(id);
        repo.deleteById(id);
    }
}
