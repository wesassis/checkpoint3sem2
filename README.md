# Checkpoint API 3/3

Aplicação Java Spring Boot com CRUD de Itens, utilizando PostgreSQL, Docker e um pipeline de CI/CD com GitHub Actions.

## ⚙️ Tecnologias

-   Java 17+
-   Spring Boot 3.2.0
-   PostgreSQL
-   Docker
-   Maven
-   GitHub Actions

## ⚡ Começar Rápido

A API estará disponível em: `http://localhost:8080`

### 1. Com Docker Compose (Recomendado)

Este método sobe a API e o banco de dados PostgreSQL automaticamente.

```bash
docker-compose up --build
```

### 2. Com Maven (Desenvolvimento Local)

*Certifique-se de ter um PostgreSQL rodando localmente na porta `5432`.*

```bash
mvn clean spring-boot:run
```

### 3. Com Docker Hub

Puxar e rodar a imagem mais recente já buildada.

```bash
docker pull wesassis/checkpoint-api:latest
docker run -p 8080:8080 wesassis/checkpoint-api:latest
```

## 📚 API (Swagger) & Endpoints

Acesse a documentação interativa do Swagger em:
`http://localhost:8080/swagger-ui.html`

### Endpoints Principais

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/itens` | Criar um novo item |
| `GET` | `/api/itens` | Listar todos os itens |
| `GET` | `/api/itens/{id}` | Buscar um item por ID |
| `PUT` | `/api/itens/{id}` | Atualizar um item por ID |
| `DELETE` | `/api/itens/{id}` | Deletar um item por ID |

## 📝 Exemplos de Requisição (cURL)

### Criar um item

```bash
curl -X POST http://localhost:8080/api/itens \
 -H "Content-Type: application/json" \
 -d '{"nome": "Meu Item", "descricao": "Descrição"}'
```

### Listar itens

```bash
curl http://localhost:8080/api/itens
```

### Buscar item por ID (Ex: ID 1)

```bash
curl http://localhost:8080/api/itens/1
```

### Atualizar item (Ex: ID 1)

```bash
curl -X PUT http://localhost:8080/api/itens/1 \
 -H "Content-Type: application/json" \
 -d '{"nome": "Item Atualizado", "descricao": "Nova descrição"}'
```

### Deletar item (Ex: ID 1)

```bash
curl -X DELETE http://localhost:8080/api/itens/1
```

## 🧪 Testes

### Testes Unitários

```bash
mvn test
```

### Testes de Integração

```bash
mvn verify
```

## 🔄 CI/CD Pipeline (GitHub Actions)

### Workflows

-   **ci.yml**: Roda testes e build em branches: `develop`, `feature/*`, `hotfix/*`
-   **cd_upload.yml**: Faz upload da imagem Docker Hub em pushes para: `develop`, `main`, e em pull requests
-   **release.yml**: Cria versionamento automático e releases na branch: `main`
-   **cd\_integration\_test.yml**: Roda testes de integração com Docker em: `develop`, `main`

### Configuração de Secrets

Adicione os seguintes secrets no repositório do GitHub (`Settings` > `Secrets and variables` > `Actions`):

-   `DOCKER_USERNAME`: Seu nome de usuário do Docker Hub.
-   `DOCKER_PASSWORD`: Seu token de acesso (password) do Docker Hub.

## 🐳 Configuração (Docker & Variáveis)

### Docker Compose

Serviços definidos no `docker-compose.yml`:

-   **api**: A aplicação Spring Boot (porta `8080:8080`)
-   **db**: O banco de dados PostgreSQL (porta `5432:5432`)

Credenciais padrão do banco de dados (usadas pelo `docker-compose`):

-   **Database**: `checkpoint_db`
-   **User**: `checkpoint_user`
-   **Password**: `checkpoint_password`

### Variáveis de Ambiente (Produção)

Estas são as variáveis que a aplicação espera (são injetadas pelo `docker-compose`):

```properties
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/checkpoint_db
SPRING_DATASOURCE_USERNAME=checkpoint_user
SPRING_DATASOURCE_PASSWORD=checkpoint_password
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

### Variáveis de Ambiente (Test)

Para testes, a aplicação usa um banco H2 em memória (`application-test.properties`):

```properties
SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb
SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.H2Dialect
```

## 📁 Estrutura do Projeto

```
.
├── .github/workflows/    # GitHub Actions (CI/CD)
├── src/main/java/        # Código principal da aplicação
├── src/test/java/        # Testes unitários e de integração
├── Dockerfile            # Definição do container da API
├── docker-compose.yml    # Orquestração (API + Banco de Dados)
└── pom.xml               # Dependências e build (Maven)
```

## 🎓 Dependências Principais

-   Spring Boot Starter Web
-   Spring Data JPA
-   PostgreSQL Driver
-   Spring Boot Actuator (Monitoramento)
-   Springdoc OpenAPI (Swagger UI)
-   TestContainers (Testes de integração)
-   H2 Database (Testes unitários)

## 👥 Integrantes

-   **Pablo Rangel** - RM: 551548
-   **Guilherme Cavalcanti** - RM: 98928
-   **Wesley Assis** - RM: 552516
