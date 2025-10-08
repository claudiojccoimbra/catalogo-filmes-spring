package com.seuprojeto.catalogo.domain;

public class Filme {
    private Long id;
    private String titulo;
    private Integer ano;
    private Integer duracaoMin;

    public Filme(){}

    public Filme(Long id, String titulo, Integer ano, Integer duracaoMin){
        this.id = id;
        this.titulo = titulo;
        this.ano = ano;
        this.duracaoMin = duracaoMin;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }
    public Integer getDuracaoMin() { return duracaoMin; }
    public void setDuracaoMin(Integer duracaoMin) { this.duracaoMin = duracaoMin; }
}
