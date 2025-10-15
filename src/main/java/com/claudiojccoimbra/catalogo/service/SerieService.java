package com.claudiojccoimbra.catalogo.service;
import com.claudiojccoimbra.catalogo.domain.Serie;
import com.claudiojccoimbra.catalogo.exception.SerieNaoEncontradaException;
import com.claudiojccoimbra.catalogo.repository.SerieRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SerieService implements CrudService<Serie, Long> {
    private final SerieRepository repo;
    public SerieService(SerieRepository repo){ this.repo = repo; }
    private void validar(Serie s){
        if(s==null) throw new IllegalArgumentException("SÃ©rie nula");
        if(s.getTitulo()==null || s.getTitulo().isBlank())
            throw new IllegalArgumentException("TÃ­tulo Ã© obrigatÃ³rio");
    }
    @Override public Serie incluir(Serie s){ validar(s); return repo.save(s); }
    @Override public Serie alterar(Long id, Serie s){
        validar(s);
        Serie atual = buscarPorId(id).orElseThrow(() -> new SerieNaoEncontradaException(id));
        atual.setTitulo(s.getTitulo());
        atual.setAno(s.getAno());
        atual.setTemporadas(s.getTemporadas());
        return repo.save(atual);
    }
    @Override public Optional<Serie> buscarPorId(Long id){ return repo.findById(id); }
    @Override public List<Serie> listarTodos(){ return repo.findAll(); }
    @Override public void excluir(Long id){
        if(!repo.existsById(id)) throw new SerieNaoEncontradaException(id);
        repo.deleteById(id);
    }
}