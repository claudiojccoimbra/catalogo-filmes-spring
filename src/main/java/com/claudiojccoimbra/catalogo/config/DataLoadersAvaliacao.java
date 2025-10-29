package com.claudiojccoimbra.catalogo.config;

import com.claudiojccoimbra.catalogo.controller.dto.AvaliacaoRequest;
import com.claudiojccoimbra.catalogo.repository.FilmeRepository;
import com.claudiojccoimbra.catalogo.service.AvaliacaoService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataLoadersAvaliacao {

    @Bean
    @Order(30)
    ApplicationRunner loadAvaliacoes(AvaliacaoService avaliacoes, FilmeRepository filmes) {
        return args -> {
            var res = new ClassPathResource("data/avaliacoes.txt");
            if (!res.exists()) return;

            int ok = 0;
            List<String> faltando = new ArrayList<>();

            try (var br = new BufferedReader(new InputStreamReader(res.getInputStream(), StandardCharsets.UTF_8))) {
                // carrega as linhas válidas primeiro
                List<String> linhas = br.lines()
                        .filter(l -> !l.isBlank() && !l.trim().startsWith("#"))
                        .toList();

                for (String line : linhas) {
                    var p = line.split(";", -1);

                    var titulo = p[0].trim();
                    var optFilme = filmes.findByTituloIgnoreCase(titulo);

                    if (optFilme.isPresent()) {
                        var filme = optFilme.get();
                        var req = new AvaliacaoRequest(
                                filme.getId(),
                                p[1].trim(),                     // autorEmail
                                Integer.parseInt(p[2].trim()),   // nota
                                p[3].trim(),                     // comentario
                                LocalDate.parse(p[4].trim())     // data
                        );
                        avaliacoes.incluir(req);
                        ok++;
                    } else {
                        faltando.add(titulo);
                    }
                }
            }

            System.out.println("[Loader] Avaliações carregadas: " + ok);
            if (!faltando.isEmpty()) {
                System.out.println("[Loader] Avaliações SKIPPED (filme não encontrado): " + faltando);
            }
        };
    }
}
