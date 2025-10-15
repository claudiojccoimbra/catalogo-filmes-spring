package com.claudiojccoimbra.catalogo.repository;
import com.claudiojccoimbra.catalogo.domain.Filme;
import java.util.*;
public interface FilmeRepository {
    Filme save(Filme f);
    Optional<Filme> findById(Long id);
    List<Filme> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}