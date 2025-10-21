package com.claudiojccoimbra.catalogo.repository.mem;
import com.claudiojccoimbra.catalogo.domain.DetalhesFilme;
import com.claudiojccoimbra.catalogo.repository.DetalhesFilmeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("mem")
public class DetalhesFilmeRepositoryMem implements DetalhesFilmeRepository {
    private final Map<Long,DetalhesFilme> db = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);
    @Override public DetalhesFilme save(DetalhesFilme d){ if(d.getId()==null) d.setId(seq.incrementAndGet()); db.put(d.getId(), d); return d; }
    @Override public Optional<DetalhesFilme> findById(Long id){ return Optional.ofNullable(db.get(id)); }
    @Override public List<DetalhesFilme> findAll(){ return new ArrayList<>(db.values()); }
    @Override public void deleteById(Long id){ db.remove(id); }
    @Override public boolean existsById(Long id){ return db.containsKey(id); }
}

