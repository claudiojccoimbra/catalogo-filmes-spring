package com.claudiojccoimbra.catalogo.repository.jpa;

import com.claudiojccoimbra.catalogo.domain.Estudio;
import com.claudiojccoimbra.catalogo.repository.EstudioRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class EstudioRepositoryJpaImpl implements EstudioRepository {
    private final EstudioJpaRepository jpa;
    public EstudioRepositoryJpaImpl(EstudioJpaRepository jpa){ this.jpa = jpa; }
    @Override public Estudio save(Estudio e){ return jpa.save(e); }
    @Override public Optional<Estudio> findById(Long id){ return jpa.findById(id); }
    @Override public List<Estudio> findAll(){ return jpa.findAll(); }
    @Override public void deleteById(Long id){ jpa.deleteById(id); }
    @Override public boolean existsByNomeIgnoreCase(String nome){ return jpa.existsByNomeIgnoreCase(nome); }
}
