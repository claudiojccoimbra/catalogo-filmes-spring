# Catálogo de Filmes — Features 1 e 2 (memória)

API REST em **Spring Boot 3.3** + **Java 21** com arquitetura em camadas (**Controller → Service → Repository**).

- **Feature 1**: bootstrap do projeto e CRUD de **Filme** em memória.  
- **Feature 2**: expansão do domínio com **herança** (`Obra` ← `Filme`/`Serie`), **associação 1–1** (`Filme` ↔ `DetalhesFilme`), **CRUDs completos** para `Filme`, `Serie` e `Estudio`, além de **loaders** de dados.

## Ambiente
- Java 21 (Temurin/Adoptium)  
- Maven 3.9+  
- Spring Boot 3.3.x  
- VS Code (opcional) + extensão REST Client (opcional)

## Modelo de domínio
- **Herança**: `Obra` (abstrata) ← `Filme`, `Serie`  
- **Associação 1–1**: `Filme` ↔ `DetalhesFilme`  
- **Entidades**  
  - `Obra`: `id`, `titulo`, `ano`  
  - `Filme`: `duracaoMin`, `ativo`, `detalhes: DetalhesFilme`  
  - `Serie`: `temporadas`  
  - `Estudio`: `nome`  
  - `DetalhesFilme`: `sinopse`, `idioma`, `classificacao`

## Como executar
```bash
mvn spring-boot:run
# ou
mvn clean package
java -jar target/catalogo-filmes-spring-0.0.1-SNAPSHOT.jar
```

## Endpoints

### Filmes — `/api/v1/filmes`
- `POST /` – cria  
- `GET /` – lista  
- `GET /{id}` – busca por id  
- `PUT /{id}` – atualiza  
- `PATCH /{id}/inativar` – define `ativo=false`  
- `DELETE /{id}` – remove

Exemplo de criação:
```json
{
  "titulo": "Toy Story 2",
  "ano": 1999,
  "duracaoMin": 92,
  "detalhes": { "sinopse": "Continuação", "idioma": "PT-BR", "classificacao": "Livre" }
}
```

### Séries — `/api/v1/series`
- `POST /`, `GET /`, `GET /{id}`, `PUT /{id}`, `DELETE /{id}`

Exemplo de criação:
```json
{ "titulo": "Loki", "ano": 2021, "temporadas": 2 }
```

### Estúdios — `/api/v1/estudios`
- `POST /`, `GET /`, `GET /{id}`, `PUT /{id}`, `DELETE /{id}`

Exemplo de criação:
```json
{ "nome": "Pixar" }
```

## Dados de exemplo (loaders)
Se existirem, são carregados na inicialização:
```
src/main/resources/data/filmes.txt   # titulo;ano;duracaoMin;ativo;sinopse;idioma;classificacao
src/main/resources/data/series.txt   # titulo;ano;temporadas
```

## Estrutura
```
src/main/java/com/claudiojccoimbra/catalogo/
 ├─ CatalogoApplication.java
 ├─ config/DataLoaders.java
 ├─ controller/ (FilmeController, SerieController, EstudioController)
 ├─ domain/ (Obra, Filme, Serie, Estudio, DetalhesFilme)
 ├─ exception/ (...)
 ├─ repository/ (interfaces) + repository/mem/ (implementações em memória)
 └─ service/ (CrudService, FilmeService, SerieService, EstudioService)
src/main/resources/data/ (filmes.txt, series.txt)
```

## Git
```bash
git add README.md
git commit -m "docs: atualiza README com Feature 2"
git push
```

Repositório: https://github.com/claudiojccoimbra/catalogo-filmes-spring
