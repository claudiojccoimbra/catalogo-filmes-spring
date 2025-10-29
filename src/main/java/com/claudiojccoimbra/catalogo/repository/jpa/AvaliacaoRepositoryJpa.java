package com.claudiojccoimbra.catalogo.repository.jpa;

import com.claudiojccoimbra.catalogo.domain.Avaliacao;
import com.claudiojccoimbra.catalogo.repository.AvaliacaoRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface AvaliacaoRepositoryJpa
        extends JpaRepository<Avaliacao, Long>, AvaliacaoRepository {
}
