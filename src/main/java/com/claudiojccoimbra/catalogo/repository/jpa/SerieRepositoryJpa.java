package com.claudiojccoimbra.catalogo.repository.jpa;

import com.claudiojccoimbra.catalogo.domain.Serie;
import com.claudiojccoimbra.catalogo.repository.SerieRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface SerieRepositoryJpa
        extends JpaRepository<Serie, Long>, SerieRepository {
}
