package com.claudiojccoimbra.catalogo.repository.jpa;

import com.claudiojccoimbra.catalogo.domain.DetalhesFilme;
import com.claudiojccoimbra.catalogo.repository.DetalhesFilmeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class DetalhesFilmeRepositoryJpaImpl implements DetalhesFilmeRepository {
    private final DetalhesFilmeJpaRepository jpa;
    public DetalhesFilmeRepositoryJpaImpl(DetalhesFilmeJpaRepository jpa){ this.jpa = jpa; }
    @Override public DetalhesFilme save(DetalhesFilme d){ return jpa.save(d); }
    @Override public Optional<DetalhesFilme> findById(Long id){ return jpa.findById(id); }
    @Override public List<DetalhesFilme> findAll(){ return jpa.findAll(); }
    @Override public void deleteById(Long id){ jpa.deleteById(id); }
    @Override public boolean existsById(Long id){ return jpa.existsById(id); }
}
