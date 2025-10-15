package com.claudiojccoimbra.catalogo.service;
import com.claudiojccoimbra.catalogo.domain.DetalhesFilme;
import com.claudiojccoimbra.catalogo.domain.Filme;
import com.claudiojccoimbra.catalogo.exception.FilmeInvalidoException;
import com.claudiojccoimbra.catalogo.exception.FilmeNaoEncontradoException;
import com.claudiojccoimbra.catalogo.repository.DetalhesFilmeRepository;
import com.claudiojccoimbra.catalogo.repository.FilmeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FilmeService implements CrudService<Filme, Long> {
    private final FilmeRepository repo;
    private final DetalhesFilmeRepository detalhesRepo;

    public FilmeService(FilmeRepository repo, DetalhesFilmeRepository detalhesRepo){
        this.repo = repo; this.detalhesRepo = detalhesRepo;
    }
    private void validar(Filme f){
        if(f == null) throw new FilmeInvalidoException("Filme nulo");
        if(f.getTitulo()==null || f.getTitulo().isBlank())
            throw new FilmeInvalidoException("TÃ­tulo Ã© obrigatÃ³rio");
        if(f.getDuracaoMin()!=null && f.getDuracaoMin() <= 0)
            throw new FilmeInvalidoException("DuraÃ§Ã£o deve ser positiva");
    }
    @Override public Filme incluir(Filme f){
        validar(f);
        if(f.getDetalhes()!=null){
            DetalhesFilme d = detalhesRepo.save(f.getDetalhes());
            f.setDetalhes(d);
        }
        return repo.save(f);
    }
    @Override public Filme alterar(Long id, Filme f){
        validar(f);
        Filme atual = buscarPorId(id).orElseThrow(() -> new FilmeNaoEncontradoException(id));
        atual.setTitulo(f.getTitulo());
        atual.setAno(f.getAno());
        atual.setDuracaoMin(f.getDuracaoMin());
        if(f.getAtivo()!=null) atual.setAtivo(f.getAtivo());
        if(f.getDetalhes()!=null){
            DetalhesFilme d = f.getDetalhes();
            if(d.getId()==null){ d = detalhesRepo.save(d); }
            atual.setDetalhes(d);
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