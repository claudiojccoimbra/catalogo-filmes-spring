package com.claudiojccoimbra.catalogo.repository.jpa;
import com.claudiojccoimbra.catalogo.domain.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmeJpaRepository extends JpaRepository<Filme, Long> {}
