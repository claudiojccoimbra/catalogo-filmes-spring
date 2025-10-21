package com.claudiojccoimbra.catalogo.repository.jpa;
import com.claudiojccoimbra.catalogo.domain.Estudio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudioJpaRepository extends JpaRepository<Estudio, Long> {
    boolean existsByNomeIgnoreCase(String nome);
}
