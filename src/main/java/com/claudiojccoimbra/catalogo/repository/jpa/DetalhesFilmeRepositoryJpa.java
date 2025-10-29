package com.claudiojccoimbra.catalogo.repository.jpa;

import com.claudiojccoimbra.catalogo.domain.DetalhesFilme;
import com.claudiojccoimbra.catalogo.repository.DetalhesFilmeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface DetalhesFilmeRepositoryJpa
        extends JpaRepository<DetalhesFilme, Long>, DetalhesFilmeRepository {
}
