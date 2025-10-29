# Catálogo de Filmes — Features 1, 2, 3 e 4
API REST em **Spring Boot 3.3** + **Java 21** com arquitetura em camadas (**Controller → Service → Repository**) e repositórios hexagonais (interfaces de domínio + adapters JPA).

- **Feature 1**: bootstrap + CRUD de **Filme** em memória.  
- **Feature 2**: domínio com **herança** (`Obra` ← `Filme`/`Serie`), **1–1** (`Filme` ↔ `DetalhesFilme`), **CRUDs** `Filme`, `Serie`, `Estudio` + **loaders**.  
- **Feature 3**: **persistência com Spring Data JPA + H2**, `@Entity`/`@Id`, `@MappedSuperclass` em `Obra`, **cascade** 1–1 para `Filme.detalhes`, **profile `jpa`** e **H2 Console**.  
- **Feature 4**: **validação avançada (Bean Validation)**, **relacionamento 1–N** (`Filme` → `Avaliacao`), **GlobalExceptionHandler** com payload consistente (**ApiError**), **loaders** de avaliações, **tests.http** e ajustes de robustez (Jackson nas entidades, `open-in-view=false`, UTF-8).

---

## Ambiente
- Java 21 (Temurin/Adoptium)  
- Maven 3.9+  
- Spring Boot 3.3.x  
- VS Code (opcional) + extensão **REST Client** (opcional) para `tests.http`

---

## Como executar
```bash
# Executar com o profile padrão (jpa) e UTF-8 (Windows)
mvn spring-boot:run -D"spring-boot.run.jvmArguments=-Dfile.encoding=UTF-8"

# Build jar
mvn clean package
java -Dfile.encoding=UTF-8 -jar target/catalogo-filmes-spring-0.0.1-SNAPSHOT.jar
