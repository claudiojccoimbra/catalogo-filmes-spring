package com.claudiojccoimbra.catalogo.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {
    T salvar(T obj);
    Optional<T> buscarPorId(ID id);
    List<T> listarTodos();
    void excluir(ID id);
}
