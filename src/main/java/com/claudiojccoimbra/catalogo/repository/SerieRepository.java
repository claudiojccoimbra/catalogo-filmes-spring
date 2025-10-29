package com.claudiojccoimbra.catalogo.repository;
import com.claudiojccoimbra.catalogo.domain.Serie;
import java.util.*;

public interface SerieRepository {
    Serie save(Serie s);
    Optional<Serie> findById(Long id);
    List<Serie> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
