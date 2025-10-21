package com.claudiojccoimbra.catalogo.repository.jpa;
import com.claudiojccoimbra.catalogo.domain.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieJpaRepository extends JpaRepository<Serie, Long> {}
