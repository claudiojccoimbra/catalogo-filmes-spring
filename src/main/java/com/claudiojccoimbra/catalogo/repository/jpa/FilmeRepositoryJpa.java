package com.claudiojccoimbra.catalogo.repository.jpa;

import com.claudiojccoimbra.catalogo.domain.Filme;
import com.claudiojccoimbra.catalogo.repository.FilmeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("jpa")
public interface FilmeRepositoryJpa extends JpaRepository<Filme, Long>, FilmeRepository {
    @Override
    Optional<Filme> findByTituloIgnoreCase(String titulo);
}
