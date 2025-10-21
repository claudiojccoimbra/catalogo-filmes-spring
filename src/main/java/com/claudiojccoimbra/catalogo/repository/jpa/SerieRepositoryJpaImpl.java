package com.claudiojccoimbra.catalogo.repository.jpa;

import com.claudiojccoimbra.catalogo.domain.Serie;
import com.claudiojccoimbra.catalogo.repository.SerieRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class SerieRepositoryJpaImpl implements SerieRepository {
    private final SerieJpaRepository jpa;
    public SerieRepositoryJpaImpl(SerieJpaRepository jpa){ this.jpa = jpa; }
    @Override public Serie save(Serie s){ return jpa.save(s); }
    @Override public Optional<Serie> findById(Long id){ return jpa.findById(id); }
    @Override public List<Serie> findAll(){ return jpa.findAll(); }
    @Override public void deleteById(Long id){ jpa.deleteById(id); }
    @Override public boolean existsById(Long id){ return jpa.existsById(id); }
}
