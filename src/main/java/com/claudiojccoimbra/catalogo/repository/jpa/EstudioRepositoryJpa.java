package com.claudiojccoimbra.catalogo.repository.jpa;

import com.claudiojccoimbra.catalogo.domain.Estudio;
import com.claudiojccoimbra.catalogo.repository.EstudioRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("jpa")
public interface EstudioRepositoryJpa
        extends JpaRepository<Estudio, Long>, EstudioRepository {

    // Spring Data implementa automaticamente por derived query
    boolean existsByNomeIgnoreCase(String nome);
    Optional<Estudio> findByNomeIgnoreCase(String nome);
}
