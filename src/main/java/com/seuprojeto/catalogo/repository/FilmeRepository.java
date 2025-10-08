package com.seuprojeto.catalogo.repository;

import com.seuprojeto.catalogo.domain.Filme;
import java.util.*;

public interface FilmeRepository {
    Filme save(Filme f);
    Optional<Filme> findById(Long id);
    List<Filme> findAll();
    void deleteById(Long id);
}
