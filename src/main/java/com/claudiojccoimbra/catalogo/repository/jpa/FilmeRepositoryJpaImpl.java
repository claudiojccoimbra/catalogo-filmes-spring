package com.claudiojccoimbra.catalogo.repository.jpa;

import com.claudiojccoimbra.catalogo.domain.Filme;
import com.claudiojccoimbra.catalogo.repository.FilmeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class FilmeRepositoryJpaImpl implements FilmeRepository {
    private final FilmeJpaRepository jpa;
    public FilmeRepositoryJpaImpl(FilmeJpaRepository jpa){ this.jpa = jpa; }
    @Override public Filme save(Filme f){ return jpa.save(f); }
    @Override public Optional<Filme> findById(Long id){ return jpa.findById(id); }
    @Override public List<Filme> findAll(){ return jpa.findAll(); }
    @Override public void deleteById(Long id){ jpa.deleteById(id); }
    @Override public boolean existsById(Long id){ return jpa.existsById(id); }
}
