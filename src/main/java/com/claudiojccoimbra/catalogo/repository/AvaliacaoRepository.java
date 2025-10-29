package com.claudiojccoimbra.catalogo.repository;

import com.claudiojccoimbra.catalogo.domain.Avaliacao;
import java.util.List;

public interface AvaliacaoRepository {
    Avaliacao save(Avaliacao a);
    List<Avaliacao> findByFilme_IdOrderByDataDesc(Long filmeId);
    List<Avaliacao> findByFilme_TituloContainingIgnoreCaseAndNotaGreaterThanEqual(String parteTitulo, int notaMin);
    List<Avaliacao> findByAutorEmailEndingWithIgnoreCase(String dominio);
}
