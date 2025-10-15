package com.claudiojccoimbra.catalogo.domain;

public class Serie extends Obra {
    private Integer temporadas;

    public Serie(){}
    public Serie(Long id, String titulo, Integer ano, Integer temporadas){
        setId(id); setTitulo(titulo); setAno(ano);
        this.temporadas = temporadas;
    }
    public Integer getTemporadas() { return temporadas; }
    public void setTemporadas(Integer temporadas) { this.temporadas = temporadas; }
}