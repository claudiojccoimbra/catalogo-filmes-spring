package com.claudiojccoimbra.catalogo.exception;
public class SerieNaoEncontradaException extends RuntimeException {
    public SerieNaoEncontradaException(Long id){ super("SÃ©rie nÃ£o encontrada: id=" + id); }
}