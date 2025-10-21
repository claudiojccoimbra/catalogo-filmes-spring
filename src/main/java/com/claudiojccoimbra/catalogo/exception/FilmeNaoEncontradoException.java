package com.claudiojccoimbra.catalogo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FilmeNaoEncontradoException extends RuntimeException {
    public FilmeNaoEncontradoException(Long id){ super("Filme nÃƒÂ£o encontrado: id=" + id); }
}
