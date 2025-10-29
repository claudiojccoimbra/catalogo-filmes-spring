package com.claudiojccoimbra.catalogo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "filmes")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Filme extends Obra {

    private Integer duracaoMin;
    private Boolean ativo = true;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true /* default fetch em OneToOne é EAGER */)
    @JoinColumn(name = "detalhes_id", unique = true)
    private DetalhesFilme detalhes;

    @JsonIgnore
    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Avaliacao> avaliacoes = new ArrayList<>();

    public Filme() {}
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

    public List<Avaliacao> getAvaliacoes() { return avaliacoes; }
    public void setAvaliacoes(List<Avaliacao> avaliacoes) { this.avaliacoes = avaliacoes; }

    @Override
    public String toString() {
        return "Filme{id=" + getId() + ", titulo='" + getTitulo() + "', ano=" + getAno() +
               ", duracaoMin=" + duracaoMin + ", ativo=" + ativo + "}";
    }
}
