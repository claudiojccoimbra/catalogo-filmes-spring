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