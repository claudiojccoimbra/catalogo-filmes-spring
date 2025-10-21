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

    public FilmeService(FilmeRepository repo){
        this.repo = repo;
    }

    private void validar(Filme f){
        if(f == null) throw new FilmeInvalidoException("Filme nulo");
        if(f.getTitulo()==null || f.getTitulo().isBlank())
            throw new FilmeInvalidoException("Título é obrigatório");
        if(f.getDuracaoMin()!=null && f.getDuracaoMin() <= 0)
            throw new FilmeInvalidoException("Duração deve ser positiva");
    }

    @Override
    public Filme incluir(Filme f){
        validar(f);
        // não salve DetalhesFilme separadamente; o cascade do Filme fará isso
        if (f.getDetalhes()!=null && f.getDetalhes().getId()!=null) {
            throw new FilmeInvalidoException("DetalhesFilme novo não deve ter id");
        }
        return repo.save(f);
    }

    @Override
    public Filme alterar(Long id, Filme f){
        validar(f);
        Filme atual = buscarPorId(id).orElseThrow(() -> new FilmeNaoEncontradoException(id));
        atual.setTitulo(f.getTitulo());
        atual.setAno(f.getAno());
        atual.setDuracaoMin(f.getDuracaoMin());
        if(f.getAtivo()!=null) atual.setAtivo(f.getAtivo());

        // Atualiza os campos do detalhe sem trocar a referência (evita detached)
        if(f.getDetalhes()!=null){
            DetalhesFilme d = atual.getDetalhes();
            if (d == null) {
                d = new DetalhesFilme();
                atual.setDetalhes(d);
            }
            d.setSinopse(f.getDetalhes().getSinopse());
            d.setIdioma(f.getDetalhes().getIdioma());
            d.setClassificacao(f.getDetalhes().getClassificacao());
        }
        return repo.save(atual);
    }

    public Filme inativar(Long id){
        Filme atual = buscarPorId(id).orElseThrow(() -> new FilmeNaoEncontradoException(id));
        atual.setAtivo(false);
        return repo.save(atual);
    }

    @Override public Optional<Filme> buscarPorId(Long id){ return repo.findById(id); }
    @Override public List<Filme> listarTodos(){ return repo.findAll(); }
    @Override public void excluir(Long id){
        if(!repo.existsById(id)) throw new FilmeNaoEncontradoException(id);
        repo.deleteById(id);
    }
}
