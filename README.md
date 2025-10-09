# Catálogo de Filmes — Feature 1 (Memória)

API REST com Spring Boot 3.3 + Java 21 (CRUD de Filmes em memória, camadas Controller/Service/Repository).

## Rodar
```bash
mvn spring-boot:run
```

Endpoints base: `http://localhost:8080/api/v1/filmes`

- `POST /` cria (JSON: titulo, ano, duracaoMin)
- `GET /` lista
- `GET /{id}` obtém
- `PUT /{id}` atualiza
- `DELETE /{id}` remove

Swagger: `http://localhost:8080/swagger-ui.html`
