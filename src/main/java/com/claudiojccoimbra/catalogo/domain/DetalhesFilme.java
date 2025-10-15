package com.claudiojccoimbra.catalogo.domain;

public class DetalhesFilme {
    private Long id;
    private String sinopse;
    private String idioma;
    private String classificacao;

    public DetalhesFilme(){}
    public DetalhesFilme(Long id, String sinopse, String idioma, String classificacao){
        this.id = id; this.sinopse = sinopse; this.idioma = idioma; this.classificacao = classificacao;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public String getClassificacao() { return classificacao; }
    public void setClassificacao(String classificacao) { this.classificacao = classificacao; }
}