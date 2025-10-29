package com.claudiojccoimbra.catalogo.repository;

import com.claudiojccoimbra.catalogo.domain.Estudio;
import java.util.*;

public interface EstudioRepository {
    Estudio save(Estudio e);
    Optional<Estudio> findById(Long id);
    List<Estudio> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);

    boolean existsByNomeIgnoreCase(String nome);
    Optional<Estudio> findByNomeIgnoreCase(String nome);
}
