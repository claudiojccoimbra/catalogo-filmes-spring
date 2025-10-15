package com.claudiojccoimbra.catalogo.domain;

public class Filme extends Obra {
    private Integer duracaoMin;
    private Boolean ativo = true;
    private DetalhesFilme detalhes;

    public Filme(){}

    public Filme(Long id, String titulo, Integer ano, Integer duracaoMin){
        setId(id); setTitulo(titulo); setAno(ano);
        this.duracaoMin = duracaoMin;
        this.ativo = true;
    }

    public Integer getDuracaoMin() { return duracaoMin; }
    public void setDuracaoMin(Integer duracaoMin) { this.duracaoMin = duracaoMin; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
    public DetalhesFilme getDetalhes() { return detalhes; }
    public void setDetalhes(DetalhesFilme detalhes) { this.detalhes = detalhes; }

    @Override
    public String toString() {
        return "Filme{id=" + getId() + ", titulo='" + getTitulo() + "', ano=" + getAno() +
               ", duracaoMin=" + duracaoMin + ", ativo=" + ativo + "}";
    }
}