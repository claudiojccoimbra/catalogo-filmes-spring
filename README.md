# Catálogo de Filmes — Features 1, 2 e 3 (memória + JPA/H2)

API REST em **Spring Boot 3.3** + **Java 21** com arquitetura em camadas (**Controller → Service → Repository**).

- **Feature 1**: bootstrap do projeto e CRUD de **Filme** em memória.  
- **Feature 2**: expansão do domínio com **herança** (`Obra` ← `Filme`/`Serie`), **associação 1–1** (`Filme` ↔ `DetalhesFilme`), **CRUDs completos** para `Filme`, `Serie` e `Estudio`, além de **loaders** de dados.  
- **Feature 3**: **persistência com Spring Data JPA + H2**, **repositories JPA**, `@Entity`/`@Id` nas entidades, `@MappedSuperclass` em `Obra`, **cascade** 1–1 em `Filme.detalhes`, **profile `jpa`** e **H2 Console**.

---

## Ambiente
- Java 21 (Temurin/Adoptium)  
- Maven 3.9+  
- Spring Boot 3.3.x  
- VS Code (opcional) + extensão REST Client (opcional)

---

## Modelo de domínio
- **Herança**: `Obra` (abstrata, `@MappedSuperclass`) ← `Filme`, `Serie`  
- **Associação 1–1**: `Filme` ↔ `DetalhesFilme` (`@OneToOne(cascade = ALL, orphanRemoval = true)`)  
- **Entidades**  
  - `Obra`: `id`, `titulo`, `ano`  
  - `Filme`: `duracaoMin`, `ativo`, `detalhes: DetalhesFilme`  
  - `Serie`: `temporadas`  
  - `Estudio`: `nome`  
  - `DetalhesFilme`: `sinopse`, `idioma`, `classificacao`

---

## Perfis (profiles) e banco de dados

- **`jpa` (padrão)**: usa **H2 em memória** com **Spring Data JPA**.  
  - Console: acesse `http://localhost:8080/h2-console`  
  - **JDBC URL**: `jdbc:h2:mem:catalogo`  
  - **User**: `sa` (sem senha)

- **Persistência em arquivo (opcional)**: mude a URL para  
  `jdbc:h2:file:./data/catalogo-db` em `application.yml` (e mantenha o profile `jpa`).

- **Repositório em memória (feature 1/2)**: rode com o profile **default**:
  ```bash
  mvn spring-boot:run -Dspring-boot.run.profiles=default
