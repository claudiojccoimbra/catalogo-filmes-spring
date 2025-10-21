package com.claudiojccoimbra.catalogo.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "estudios")
public class Estudio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    public Estudio(){}
    public Estudio(Long id, String nome){ this.id=id; this.nome=nome; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
