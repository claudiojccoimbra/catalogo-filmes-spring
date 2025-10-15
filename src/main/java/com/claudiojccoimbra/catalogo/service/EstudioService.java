package com.claudiojccoimbra.catalogo.service;
import com.claudiojccoimbra.catalogo.domain.Estudio;
import com.claudiojccoimbra.catalogo.repository.EstudioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EstudioService {
    private final EstudioRepository repo;
    public EstudioService(EstudioRepository repo){ this.repo = repo; }
    public Estudio incluir(Estudio e){
        if(e.getNome()==null || e.getNome().isBlank())
            throw new IllegalArgumentException("Nome Ã© obrigatÃ³rio");
        if(repo.existsByNomeIgnoreCase(e.getNome()))
            throw new IllegalArgumentException("EstÃºdio jÃ¡ existe");
        return repo.save(e);
    }
    public List<Estudio> listarTodos(){ return repo.findAll(); }
    public Estudio buscarPorId(Long id){ return repo.findById(id).orElseThrow(() -> new NoSuchElementException("EstÃºdio nÃ£o encontrado")); }
    public Estudio alterar(Long id, Estudio e){
        Estudio atual = buscarPorId(id);
        atual.setNome(e.getNome());
        return repo.save(atual);
    }
    public void excluir(Long id){ repo.deleteById(id); }
}