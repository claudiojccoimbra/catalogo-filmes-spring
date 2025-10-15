package com.claudiojccoimbra.catalogo.repository.mem;
import com.claudiojccoimbra.catalogo.domain.Serie;
import com.claudiojccoimbra.catalogo.repository.SerieRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile({ "default", "mem" })
public class SerieRepositoryMem implements SerieRepository {
    private final Map<Long,Serie> db = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);
    @Override public Serie save(Serie s){ if(s.getId()==null) s.setId(seq.incrementAndGet()); db.put(s.getId(), s); return s; }
    @Override public Optional<Serie> findById(Long id){ return Optional.ofNullable(db.get(id)); }
    @Override public List<Serie> findAll(){ return new ArrayList<>(db.values()); }
    @Override public void deleteById(Long id){ db.remove(id); }
    @Override public boolean existsById(Long id){ return db.containsKey(id); }
}