package com.claudiojccoimbra.catalogo.repository;
import com.claudiojccoimbra.catalogo.domain.DetalhesFilme;
import java.util.*;

public interface DetalhesFilmeRepository {
    DetalhesFilme save(DetalhesFilme d);
    Optional<DetalhesFilme> findById(Long id);
    List<DetalhesFilme> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
