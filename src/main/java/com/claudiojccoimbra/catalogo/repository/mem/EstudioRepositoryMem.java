package com.claudiojccoimbra.catalogo.repository.mem;

import com.claudiojccoimbra.catalogo.domain.Estudio;
import com.claudiojccoimbra.catalogo.repository.EstudioRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("mem")
public class EstudioRepositoryMem implements EstudioRepository {
    private final Map<Long, Estudio> db = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    @Override public Estudio save(Estudio e){
        if(e.getId()==null) e.setId(seq.incrementAndGet());
        db.put(e.getId(), e);
        return e;
    }
    @Override public Optional<Estudio> findById(Long id){ return Optional.ofNullable(db.get(id)); }
    @Override public List<Estudio> findAll(){ return new ArrayList<>(db.values()); }
    @Override public void deleteById(Long id){ db.remove(id); }
    @Override public boolean existsById(Long id){ return db.containsKey(id); }

    @Override public boolean existsByNomeIgnoreCase(String nome){
        return db.values().stream().anyMatch(e ->
            e.getNome()!=null && e.getNome().equalsIgnoreCase(nome));
    }
    @Override public Optional<Estudio> findByNomeIgnoreCase(String nome){
        return db.values().stream()
                 .filter(e -> e.getNome()!=null && e.getNome().equalsIgnoreCase(nome))
                 .findFirst();
    }
}
