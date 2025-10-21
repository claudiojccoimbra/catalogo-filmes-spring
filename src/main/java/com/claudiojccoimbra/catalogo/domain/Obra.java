package com.claudiojccoimbra.catalogo.domain;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class Obra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private Integer ano;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }
}