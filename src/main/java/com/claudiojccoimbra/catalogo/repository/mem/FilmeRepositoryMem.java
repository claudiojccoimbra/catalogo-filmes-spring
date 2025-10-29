package com.claudiojccoimbra.catalogo.repository.mem;

import com.claudiojccoimbra.catalogo.domain.Filme;
import com.claudiojccoimbra.catalogo.repository.FilmeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("mem")
public class FilmeRepositoryMem implements FilmeRepository {
    private final Map<Long,Filme> db = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    @Override public Filme save(Filme f){ if(f.getId()==null) f.setId(seq.incrementAndGet()); db.put(f.getId(), f); return f; }
    @Override public Optional<Filme> findById(Long id){ return Optional.ofNullable(db.get(id)); }
    @Override public List<Filme> findAll(){ return new ArrayList<>(db.values()); }
    @Override public void deleteById(Long id){ db.remove(id); }
    @Override public boolean existsById(Long id){ return db.containsKey(id); }

    @Override public Optional<Filme> findByTituloIgnoreCase(String titulo){
        return db.values().stream().filter(f -> f.getTitulo()!=null && f.getTitulo().equalsIgnoreCase(titulo)).findFirst();
    }
}
