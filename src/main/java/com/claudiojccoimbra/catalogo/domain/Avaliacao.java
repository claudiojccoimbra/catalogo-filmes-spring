package com.claudiojccoimbra.catalogo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "avaliacoes")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email @NotBlank
    @Column(nullable = false, length = 255)
    private String autorEmail;

    @Min(0) @Max(10)
    @Column(nullable = false)
    private Integer nota;

    @Size(max = 500)
    private String comentario;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDate data;

    @JsonIgnore // evita recursão e problemas de lazy ao serializar avaliações
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "filme_id")
    private Filme filme;

    public Avaliacao() {}
    public Avaliacao(String autorEmail, Integer nota, String comentario, LocalDate data, Filme filme) {
        this.autorEmail = autorEmail;
        this.nota = nota;
        this.comentario = comentario;
        this.data = data;
        this.filme = filme;
    }

    public Long getId() { return id; }
    public String getAutorEmail() { return autorEmail; }
    public void setAutorEmail(String autorEmail) { this.autorEmail = autorEmail; }
    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public Filme getFilme() { return filme; }
    public void setFilme(Filme filme) { this.filme = filme; }
}
