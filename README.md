# Checkpoint API

Aplicação Java Spring Boot com CRUD de Itens, PostgreSQL, Docker e CI/CD.



## 📋 Tecnologias## � Começar Rápido



Java 17+ | Spring Boot 3.2.0 | PostgreSQL | Docker | Maven | GitHub Actions### 1. Com Docker Compose (Recomendado)

```bash

## ⚡ Começar Rápidodocker-compose up --build

```

### 1️⃣ Docker Compose (Recomendado)A API estará em: `http://localhost:8080`

```bash

docker-compose up --build### 2. Com Maven (Desenvolvimento Local)

# Acesso: http://localhost:8080```bash

```mvn clean spring-boot:run

```

### 2️⃣ Maven LocalCertifique-se de ter PostgreSQL rodando localmente.

```bash

# Certifique-se de ter PostgreSQL rodando### 3. Com Docker Hub

mvn clean spring-boot:run```bash

```docker pull wesassis/checkpoint-api:latest

docker run -p 8080:8080 wesassis/checkpoint-api:latest

### 3️⃣ Docker Hub```

```bash

docker pull wesassis/checkpoint-api:latest## 📚 API Swagger

docker run -p 8080:8080 wesassis/checkpoint-api:latest

```Acesse a documentação interativa em: `http://localhost:8080/swagger-ui.html`



## 📚 Documentação## 🔌 Endpoints Principais



**Swagger/OpenAPI:** http://localhost:8080/swagger-ui.html| Método | Endpoint | Descrição |

|--------|----------|-----------|

## 🔌 Endpoints CRUD| POST | `/api/itens` | Criar item |

| GET | `/api/itens` | Listar itens |

| Método | Endpoint | Descrição || GET | `/api/itens/{id}` | Buscar item |

|--------|----------|-----------|| PUT | `/api/itens/{id}` | Atualizar item |

| POST | `/api/itens` | Criar item || DELETE | `/api/itens/{id}` | Deletar item |

| GET | `/api/itens` | Listar todos |

| GET | `/api/itens/{id}` | Buscar por ID |## � Exemplos de Requisição

| PUT | `/api/itens/{id}` | Atualizar |

| DELETE | `/api/itens/{id}` | Deletar |### Criar um item

```bash

## 📝 Exemplos de Requisiçãocurl -X POST http://localhost:8080/api/itens \

  -H "Content-Type: application/json" \

### Criar Item  -d '{"nome": "Meu Item", "descricao": "Descrição"}'

```bash```

curl -X POST http://localhost:8080/api/itens \

  -H "Content-Type: application/json" \### Listar itens

  -d '{"nome": "Meu Item", "descricao": "Descrição"}'```bash

```curl http://localhost:8080/api/itens

```

### Listar Todos

```bash### Atualizar item

curl http://localhost:8080/api/itens```bash

```curl -X PUT http://localhost:8080/api/itens/1 \

  -H "Content-Type: application/json" \

### Buscar por ID  -d '{"nome": "Item Atualizado"}'

```bash```

curl http://localhost:8080/api/itens/1

```### Deletar item

```bash

### Atualizarcurl -X DELETE http://localhost:8080/api/itens/1

```bash```

curl -X PUT http://localhost:8080/api/itens/1 \

  -H "Content-Type: application/json" \## 🧪 Testes

  -d '{"nome": "Atualizado", "descricao": "Nova descrição"}'

``````bash

# Testes unitários

### Deletarmvn test

```bash

curl -X DELETE http://localhost:8080/api/itens/1# Testes de integração

```mvn verify

```

## 🧪 Testes

## 🔄 CI/CD

### Testes Unitários

```bashGitHub Actions workflows configurados para:

mvn test- **CI**: Testes e build (branches: develop, feature, hotfix)

```- **CD Upload**: Docker Hub (branch: main)

- **Release**: Tags e releases (branch: main)

### Testes de Integração- **Integration Tests**: Testes com Docker (branches: develop, main)

```bash

mvn verify### Configurar Secrets

```Adicione no GitHub:

- `DOCKER_USERNAME`

## 📁 Estrutura- `DOCKER_PASSWORD`



```## 📋 Variáveis de Ambiente

src/

├── main/java/com/example/```properties

│   ├── CheckpointApiApplication.java    # Mainspring.datasource.url=jdbc:postgresql://db:5432/checkpoint_db

│   ├── model/Item.java                  # Entityspring.datasource.username=checkpoint_user

│   ├── repository/ItemRepository.java   # JPA Repositoryspring.datasource.password=checkpoint_password

│   └── controller/ItemController.java   # REST Controller```

├── main/resources/

│   ├── application.properties           # Config Produção## � Estrutura

│   └── application-test.properties      # Config Teste

└── test/java/com/example/```

    └── controller/ItemControllerIntegrationTest.java├── .github/workflows/     # GitHub Actions

```├── src/main/java/         # Código principal

├── src/test/java/         # Testes

## 🔄 CI/CD Pipeline├── Dockerfile             # Container

├── docker-compose.yml     # Orquestração

### Workflows GitHub Actions└── pom.xml               # Maven config

- **ci.yml** - Testes e build em: `develop`, `feature/*`, `hotfix/*````

- **cd_upload.yml** - Upload Docker Hub em: `develop`, `main`, pull requests

- **release.yml** - Versionamento automático em: `main`## ⚙️ Tecnologias

- **cd_integration_test.yml** - Testes de integração em: `develop`, `main`

- Java 17+

### Configurar GitHub Secrets- Spring Boot 3.2.0

1. Vá para: `Settings` → `Secrets and variables` → `Actions`- PostgreSQL

2. Adicione:- Docker

   - `DOCKER_USERNAME` = seu usuário Docker Hub- Maven

   - `DOCKER_PASSWORD` = seu token Docker Hub

## 🐳 Docker Compose

Serviços:
- **api**: Aplicação Spring Boot (porta 8080)
- **db**: PostgreSQL (porta 5432)

Credenciais padrão:
- User: `checkpoint_user`
- Password: `checkpoint_password`
- Database: `checkpoint_db`

## ⚙️ Configuração

### Variáveis de Ambiente (Production)
```properties
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/checkpoint_db
SPRING_DATASOURCE_USERNAME=checkpoint_user
SPRING_DATASOURCE_PASSWORD=checkpoint_password
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

### Variáveis de Ambiente (Test)
```properties
SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb
SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.H2Dialect
```

## 🎓 Dependências Principais

- Spring Boot Starter Web
- Spring Data JPA
- PostgreSQL Driver
- Spring Boot Actuator
- Springdoc OpenAPI (Swagger)
- TestContainers
- H2 Database (testes)

## � Integrantes

- **Pablo Rangel** - RM: 551548
- **Guilherme Cavalcanti** - RM: 98928
- **Wesley Assis** - RM: 552516
