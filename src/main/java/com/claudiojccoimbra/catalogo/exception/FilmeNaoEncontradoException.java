package com.claudiojccoimbra.catalogo.exception;
public class FilmeNaoEncontradoException extends RuntimeException {
    public FilmeNaoEncontradoException(Long id){ super("Filme nÃ£o encontrado: id=" + id); }
}