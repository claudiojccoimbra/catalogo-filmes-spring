# scripts\feature2.ps1
# Aplica a Feature 2 completa (memória) no pacote com.claudiojccoimbra.catalogo
# Escreve arquivos em UTF-8 sem BOM e remove qualquer BOM residual.

$ErrorActionPreference = "Stop"

# --- Funções auxiliares ---
function Ensure-Dir($p) { if (-not (Test-Path $p)) { New-Item -ItemType Directory -Force -Path $p | Out-Null } }
$Utf8NoBom = New-Object System.Text.UTF8Encoding($false)
function Write-NoBom($path, $content) {
  Ensure-Dir (Split-Path $path)
  [System.IO.File]::WriteAllText($path, $content, $Utf8NoBom)
}

# --- Pastas base ---
$SRC = ".\src\main\java\com\claudiojccoimbra\catalogo"
$RES = ".\src\main\resources"
Ensure-Dir $SRC
@("$SRC\domain", "$SRC\repository", "$SRC\repository\mem", "$SRC\service", "$SRC\controller", "$SRC\config", "$SRC\exception", "$RES\data") | % { Ensure-Dir $_ }

# --- groupId no pom (se ainda estiver antigo) ---
if (Test-Path .\pom.xml) {
  (Get-Content .\pom.xml -Raw) -replace '<groupId>com\.seuprojeto</groupId>', '<groupId>com.claudiojccoimbra</groupId>' | Set-Content .\pom.xml -Encoding utf8
}

# --- Application
Write-NoBom "$SRC\CatalogoApplication.java" @"
package com.claudiojccoimbra.catalogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatalogoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatalogoApplication.class, args);
    }
}
"@

# --- Domain
Write-NoBom "$SRC\domain\Obra.java" @"
package com.claudiojccoimbra.catalogo.domain;

public abstract class Obra {
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
"@

Write-NoBom "$SRC\domain\DetalhesFilme.java" @"
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
"@

Write-NoBom "$SRC\domain\Filme.java" @"
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
"@

Write-NoBom "$SRC\domain\Serie.java" @"
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
"@

Write-NoBom "$SRC\domain\Estudio.java" @"
package com.claudiojccoimbra.catalogo.domain;

public class Estudio {
    private Long id;
    private String nome;

    public Estudio(){}
    public Estudio(Long id, String nome){ this.id=id; this.nome=nome; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
"@

# --- Exceptions
Write-NoBom "$SRC\exception\FilmeInvalidoException.java" @"
package com.claudiojccoimbra.catalogo.exception;
public class FilmeInvalidoException extends RuntimeException {
    public FilmeInvalidoException(String msg){ super(msg); }
}
"@
Write-NoBom "$SRC\exception\FilmeNaoEncontradoException.java" @"
package com.claudiojccoimbra.catalogo.exception;
public class FilmeNaoEncontradoException extends RuntimeException {
    public FilmeNaoEncontradoException(Long id){ super("Filme não encontrado: id=" + id); }
}
"@
Write-NoBom "$SRC\exception\SerieNaoEncontradaException.java" @"
package com.claudiojccoimbra.catalogo.exception;
public class SerieNaoEncontradaException extends RuntimeException {
    public SerieNaoEncontradaException(Long id){ super("Série não encontrada: id=" + id); }
}
"@

# --- Repositories (interfaces)
Write-NoBom "$SRC\repository\FilmeRepository.java" @"
package com.claudiojccoimbra.catalogo.repository;
import com.claudiojccoimbra.catalogo.domain.Filme;
import java.util.*;
public interface FilmeRepository {
    Filme save(Filme f);
    Optional<Filme> findById(Long id);
    List<Filme> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
"@
Write-NoBom "$SRC\repository\SerieRepository.java" @"
package com.claudiojccoimbra.catalogo.repository;
import com.claudiojccoimbra.catalogo.domain.Serie;
import java.util.*;
public interface SerieRepository {
    Serie save(Serie s);
    Optional<Serie> findById(Long id);
    List<Serie> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
"@
Write-NoBom "$SRC\repository\EstudioRepository.java" @"
package com.claudiojccoimbra.catalogo.repository;
import com.claudiojccoimbra.catalogo.domain.Estudio;
import java.util.*;
public interface EstudioRepository {
    Estudio save(Estudio e);
    Optional<Estudio> findById(Long id);
    List<Estudio> findAll();
    void deleteById(Long id);
    boolean existsByNomeIgnoreCase(String nome);
}
"@
Write-NoBom "$SRC\repository\DetalhesFilmeRepository.java" @"
package com.claudiojccoimbra.catalogo.repository;
import com.claudiojccoimbra.catalogo.domain.DetalhesFilme;
import java.util.*;
public interface DetalhesFilmeRepository {
    DetalhesFilme save(DetalhesFilme d);
    Optional<DetalhesFilme> findById(Long id);
    List<DetalhesFilme> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
"@

# --- Repositories em memória
Write-NoBom "$SRC\repository\mem\FilmeRepositoryMem.java" @"
package com.claudiojccoimbra.catalogo.repository.mem;
import com.claudiojccoimbra.catalogo.domain.Filme;
import com.claudiojccoimbra.catalogo.repository.FilmeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile({ "default", "mem" })
public class FilmeRepositoryMem implements FilmeRepository {
    private final Map<Long,Filme> db = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);
    @Override public Filme save(Filme f){ if(f.getId()==null) f.setId(seq.incrementAndGet()); db.put(f.getId(), f); return f; }
    @Override public Optional<Filme> findById(Long id){ return Optional.ofNullable(db.get(id)); }
    @Override public List<Filme> findAll(){ return new ArrayList<>(db.values()); }
    @Override public void deleteById(Long id){ db.remove(id); }
    @Override public boolean existsById(Long id){ return db.containsKey(id); }
}
"@

Write-NoBom "$SRC\repository\mem\SerieRepositoryMem.java" @"
package com.claudiojccoimbra.catalogo.repository.mem;
import com.claudiojccoimbra.catalogo.domain.Serie;
import com.claudiojccoimbra.catalogo.repository.SerieRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile({ "default", "mem" })
public class SerieRepositoryMem implements SerieRepository {
    private final Map<Long,Serie> db = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);
    @Override public Serie save(Serie s){ if(s.getId()==null) s.setId(seq.incrementAndGet()); db.put(s.getId(), s); return s; }
    @Override public Optional<Serie> findById(Long id){ return Optional.ofNullable(db.get(id)); }
    @Override public List<Serie> findAll(){ return new ArrayList<>(db.values()); }
    @Override public void deleteById(Long id){ db.remove(id); }
    @Override public boolean existsById(Long id){ return db.containsKey(id); }
}
"@

Write-NoBom "$SRC\repository\mem\EstudioRepositoryMem.java" @"
package com.claudiojccoimbra.catalogo.repository.mem;
import com.claudiojccoimbra.catalogo.domain.Estudio;
import com.claudiojccoimbra.catalogo.repository.EstudioRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile({ "default", "mem" })
public class EstudioRepositoryMem implements EstudioRepository {
    private final Map<Long,Estudio> db = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);
    @Override public Estudio save(Estudio e){ if(e.getId()==null) e.setId(seq.incrementAndGet()); db.put(e.getId(), e); return e; }
    @Override public Optional<Estudio> findById(Long id){ return Optional.ofNullable(db.get(id)); }
    @Override public List<Estudio> findAll(){ return new ArrayList<>(db.values()); }
    @Override public void deleteById(Long id){ db.remove(id); }
    @Override public boolean existsByNomeIgnoreCase(String nome){
        return db.values().stream().anyMatch(x -> x.getNome()!=null && x.getNome().equalsIgnoreCase(nome));
    }
}
"@

Write-NoBom "$SRC\repository\mem\DetalhesFilmeRepositoryMem.java" @"
package com.claudiojccoimbra.catalogo.repository.mem;
import com.claudiojccoimbra.catalogo.domain.DetalhesFilme;
import com.claudiojccoimbra.catalogo.repository.DetalhesFilmeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile({ "default", "mem" })
public class DetalhesFilmeRepositoryMem implements DetalhesFilmeRepository {
    private final Map<Long,DetalhesFilme> db = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);
    @Override public DetalhesFilme save(DetalhesFilme d){ if(d.getId()==null) d.setId(seq.incrementAndGet()); db.put(d.getId(), d); return d; }
    @Override public Optional<DetalhesFilme> findById(Long id){ return Optional.ofNullable(db.get(id)); }
    @Override public List<DetalhesFilme> findAll(){ return new ArrayList<>(db.values()); }
    @Override public void deleteById(Long id){ db.remove(id); }
    @Override public boolean existsById(Long id){ return db.containsKey(id); }
}
"@

# --- Services
Write-NoBom "$SRC\service\CrudService.java" @"
package com.claudiojccoimbra.catalogo.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {
    T incluir(T obj);
    T alterar(ID id, T obj);
    Optional<T> buscarPorId(ID id);
    List<T> listarTodos();
    void excluir(ID id);
}
"@

Write-NoBom "$SRC\service\FilmeService.java" @"
package com.claudiojccoimbra.catalogo.service;
import com.claudiojccoimbra.catalogo.domain.DetalhesFilme;
import com.claudiojccoimbra.catalogo.domain.Filme;
import com.claudiojccoimbra.catalogo.exception.FilmeInvalidoException;
import com.claudiojccoimbra.catalogo.exception.FilmeNaoEncontradoException;
import com.claudiojccoimbra.catalogo.repository.DetalhesFilmeRepository;
import com.claudiojccoimbra.catalogo.repository.FilmeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FilmeService implements CrudService<Filme, Long> {
    private final FilmeRepository repo;
    private final DetalhesFilmeRepository detalhesRepo;

    public FilmeService(FilmeRepository repo, DetalhesFilmeRepository detalhesRepo){
        this.repo = repo; this.detalhesRepo = detalhesRepo;
    }
    private void validar(Filme f){
        if(f == null) throw new FilmeInvalidoException("Filme nulo");
        if(f.getTitulo()==null || f.getTitulo().isBlank())
            throw new FilmeInvalidoException("Título é obrigatório");
        if(f.getDuracaoMin()!=null && f.getDuracaoMin() <= 0)
            throw new FilmeInvalidoException("Duração deve ser positiva");
    }
    @Override public Filme incluir(Filme f){
        validar(f);
        if(f.getDetalhes()!=null){
            DetalhesFilme d = detalhesRepo.save(f.getDetalhes());
            f.setDetalhes(d);
        }
        return repo.save(f);
    }
    @Override public Filme alterar(Long id, Filme f){
        validar(f);
        Filme atual = buscarPorId(id).orElseThrow(() -> new FilmeNaoEncontradoException(id));
        atual.setTitulo(f.getTitulo());
        atual.setAno(f.getAno());
        atual.setDuracaoMin(f.getDuracaoMin());
        if(f.getAtivo()!=null) atual.setAtivo(f.getAtivo());
        if(f.getDetalhes()!=null){
            DetalhesFilme d = f.getDetalhes();
            if(d.getId()==null){ d = detalhesRepo.save(d); }
            atual.setDetalhes(d);
        }
        return repo.save(atual);
    }
    public Filme inativar(Long id){
        Filme atual = buscarPorId(id).orElseThrow(() -> new FilmeNaoEncontradoException(id));
        atual.setAtivo(false);
        return repo.save(atual);
    }
    @Override public Optional<Filme> buscarPorId(Long id){ return repo.findById(id); }
    @Override public List<Filme> listarTodos(){ return repo.findAll(); }
    @Override public void excluir(Long id){
        if(!repo.existsById(id)) throw new FilmeNaoEncontradoException(id);
        repo.deleteById(id);
    }
}
"@

Write-NoBom "$SRC\service\SerieService.java" @"
package com.claudiojccoimbra.catalogo.service;
import com.claudiojccoimbra.catalogo.domain.Serie;
import com.claudiojccoimbra.catalogo.exception.SerieNaoEncontradaException;
import com.claudiojccoimbra.catalogo.repository.SerieRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SerieService implements CrudService<Serie, Long> {
    private final SerieRepository repo;
    public SerieService(SerieRepository repo){ this.repo = repo; }
    private void validar(Serie s){
        if(s==null) throw new IllegalArgumentException("Série nula");
        if(s.getTitulo()==null || s.getTitulo().isBlank())
            throw new IllegalArgumentException("Título é obrigatório");
    }
    @Override public Serie incluir(Serie s){ validar(s); return repo.save(s); }
    @Override public Serie alterar(Long id, Serie s){
        validar(s);
        Serie atual = buscarPorId(id).orElseThrow(() -> new SerieNaoEncontradaException(id));
        atual.setTitulo(s.getTitulo());
        atual.setAno(s.getAno());
        atual.setTemporadas(s.getTemporadas());
        return repo.save(atual);
    }
    @Override public Optional<Serie> buscarPorId(Long id){ return repo.findById(id); }
    @Override public List<Serie> listarTodos(){ return repo.findAll(); }
    @Override public void excluir(Long id){
        if(!repo.existsById(id)) throw new SerieNaoEncontradaException(id);
        repo.deleteById(id);
    }
}
"@

Write-NoBom "$SRC\service\EstudioService.java" @"
package com.claudiojccoimbra.catalogo.service;
import com.claudiojccoimbra.catalogo.domain.Estudio;
import com.claudiojccoimbra.catalogo.repository.EstudioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EstudioService {
    private final EstudioRepository repo;
    public EstudioService(EstudioRepository repo){ this.repo = repo; }
    public Estudio incluir(Estudio e){
        if(e.getNome()==null || e.getNome().isBlank())
            throw new IllegalArgumentException("Nome é obrigatório");
        if(repo.existsByNomeIgnoreCase(e.getNome()))
            throw new IllegalArgumentException("Estúdio já existe");
        return repo.save(e);
    }
    public List<Estudio> listarTodos(){ return repo.findAll(); }
    public Estudio buscarPorId(Long id){ return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Estúdio não encontrado")); }
    public Estudio alterar(Long id, Estudio e){
        Estudio atual = buscarPorId(id);
        atual.setNome(e.getNome());
        return repo.save(atual);
    }
    public void excluir(Long id){ repo.deleteById(id); }
}
"@

# --- Controllers
Write-NoBom "$SRC\controller\FilmeController.java" @"
package com.claudiojccoimbra.catalogo.controller;
import com.claudiojccoimbra.catalogo.domain.Filme;
import com.claudiojccoimbra.catalogo.service.FilmeService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/filmes")
public class FilmeController {
    private final FilmeService service;
    public FilmeController(FilmeService service){ this.service = service; }

    @PostMapping public Filme criar(@RequestBody Filme f){ return service.incluir(f); }
    @GetMapping public List<Filme> listar(){ return service.listarTodos(); }
    @GetMapping("/{id}") public Filme obter(@PathVariable Long id){ return service.buscarPorId(id).orElse(null); }
    @PutMapping("/{id}") public Filme atualizar(@PathVariable Long id, @RequestBody Filme f){ return service.alterar(id,f); }
    @PatchMapping("/{id}/inativar") public Filme inativar(@PathVariable Long id){ return service.inativar(id); }
    @DeleteMapping("/{id}") public void remover(@PathVariable Long id){ service.excluir(id); }
}
"@

Write-NoBom "$SRC\controller\SerieController.java" @"
package com.claudiojccoimbra.catalogo.controller;
import com.claudiojccoimbra.catalogo.domain.Serie;
import com.claudiojccoimbra.catalogo.service.SerieService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/series")
public class SerieController {
    private final SerieService service;
    public SerieController(SerieService service){ this.service = service; }

    @PostMapping public Serie criar(@RequestBody Serie s){ return service.incluir(s); }
    @GetMapping public List<Serie> listar(){ return service.listarTodos(); }
    @GetMapping("/{id}") public Serie obter(@PathVariable Long id){ return service.buscarPorId(id).orElse(null); }
    @PutMapping("/{id}") public Serie atualizar(@PathVariable Long id, @RequestBody Serie s){ return service.alterar(id,s); }
    @DeleteMapping("/{id}") public void remover(@PathVariable Long id){ service.excluir(id); }
}
"@

Write-NoBom "$SRC\controller\EstudioController.java" @"
package com.claudiojccoimbra.catalogo.controller;
import com.claudiojccoimbra.catalogo.domain.Estudio;
import com.claudiojccoimbra.catalogo.service.EstudioService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/estudios")
public class EstudioController {
    private final EstudioService service;
    public EstudioController(EstudioService service){ this.service = service; }

    @PostMapping public Estudio criar(@RequestBody Estudio e){ return service.incluir(e); }
    @GetMapping public List<Estudio> listar(){ return service.listarTodos(); }
    @GetMapping("/{id}") public Estudio obter(@PathVariable Long id){ return service.buscarPorId(id); }
    @PutMapping("/{id}") public Estudio atualizar(@PathVariable Long id, @RequestBody Estudio e){ return service.alterar(id,e); }
    @DeleteMapping("/{id}") public void remover(@PathVariable Long id){ service.excluir(id); }
}
"@

# --- Loaders e dados
Write-NoBom "$SRC\config\DataLoaders.java" @"
package com.claudiojccoimbra.catalogo.config;
import com.claudiojccoimbra.catalogo.domain.DetalhesFilme;
import com.claudiojccoimbra.catalogo.domain.Filme;
import com.claudiojccoimbra.catalogo.domain.Serie;
import com.claudiojccoimbra.catalogo.service.FilmeService;
import com.claudiojccoimbra.catalogo.service.SerieService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Configuration
public class DataLoaders {
    @Bean ApplicationRunner loadFilmes(FilmeService filmes){
        return args -> {
            var res = new ClassPathResource("data/filmes.txt");
            if(!res.exists()) return;
            try(var br = new BufferedReader(new InputStreamReader(res.getInputStream(), StandardCharsets.UTF_8))){
                br.lines().filter(l -> !l.isBlank() && !l.trim().startsWith("#")).forEach(line -> {
                    // titulo;ano;duracaoMin;ativo;sinopse;idioma;classificacao
                    var p = line.split(";", -1);
                    var f = new Filme(null, p[0].trim(), Integer.parseInt(p[1].trim()), Integer.parseInt(p[2].trim()));
                    f.setAtivo(Boolean.parseBoolean(p[3].trim()));
                    var d = new DetalhesFilme(null, p.length>4?p[4].trim():null, p.length>5?p[5].trim():null, p.length>6?p[6].trim():null);
                    f.setDetalhes(d);
                    filmes.incluir(f);
                });
            }
            System.out.println("[Loader] Filmes carregados: " + filmes.listarTodos().size());
        };
    }
    @Bean ApplicationRunner loadSeries(SerieService series){
        return args -> {
            var res = new ClassPathResource("data/series.txt");
            if(!res.exists()) return;
            try(var br = new BufferedReader(new InputStreamReader(res.getInputStream(), StandardCharsets.UTF_8))){
                br.lines().filter(l -> !l.isBlank() && !l.trim().startsWith("#")).forEach(line -> {
                    // titulo;ano;temporadas
                    var p = line.split(";", -1);
                    var s = new Serie(null, p[0].trim(), Integer.parseInt(p[1].trim()), Integer.parseInt(p[2].trim()));
                    series.incluir(s);
                });
            }
            System.out.println("[Loader] Séries carregadas: " + series.listarTodos().size());
        };
    }
}
"@

Write-NoBom "$RES\data\filmes.txt" @"
# titulo;ano;duracaoMin;ativo;sinopse;idioma;classificacao
Toy Story;1995;81;true;Brinquedos ganham vida;PT-BR;Livre
Up - Altas Aventuras;2009;96;true;Velhinho e casa com balões;PT-BR;Livre
Wall-E;2008;98;true;Robô sozinho na Terra;EN;Livre
"@
Write-NoBom "$RES\data\series.txt" @"
# titulo;ano;temporadas
Breaking Bad;2008;5
Dark;2017;3
The Office;2005;9
"@

# --- Requests para VS Code (REST Client)
Write-NoBom ".\requests-feature2.http" @"
### === FILMES ===
POST http://localhost:8080/api/v1/filmes
Content-Type: application/json

{
  "titulo": "Toy Story 2",
  "ano": 1999,
  "duracaoMin": 92,
  "detalhes": { "sinopse": "Continuação", "idioma": "PT-BR", "classificacao": "Livre" }
}

PATCH http://localhost:8080/api/v1/filmes/1/inativar

GET http://localhost:8080/api/v1/filmes

### === SERIES ===
POST http://localhost:8080/api/v1/series
Content-Type: application/json

{ "titulo": "Loki", "ano": 2021, "temporadas": 2 }

GET http://localhost:8080/api/v1/series

### === ESTUDIOS ===
POST http://localhost:8080/api/v1/estudios
Content-Type: application/json

{ "nome": "Pixar" }

GET http://localhost:8080/api/v1/estudios
"@

# --- Limpa BOM residual em todos os arquivos de texto relevantes
$enc = New-Object System.Text.UTF8Encoding($false)
Get-ChildItem -Recurse -Include *.java,*.yml,*.xml,*.properties,*.http | % {
  $bytes = [System.IO.File]::ReadAllBytes($_.FullName)
  if ($bytes.Length -ge 3 -and $bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) {
    $text = [System.Text.Encoding]::UTF8.GetString($bytes, 3, $bytes.Length-3)
    [System.IO.File]::WriteAllText($_.FullName, $text, $enc)
    Write-Host "Fix BOM -> $($_.FullName)"
  }
}

Write-Host "Feature 2 aplicada com sucesso."
