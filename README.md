# Checkpoint API

Uma aplicação Java Spring Boot simples com acesso a banco de dados PostgreSQL, implementando um CRUD básico com Docker, CI/CD via GitHub Actions e testes de integração.

## 📋 Características

- **Spring Boot 3+**: Framework moderno para desenvolvimento rápido
- **Spring Data JPA**: Abstração para persistência de dados
- **PostgreSQL**: Banco de dados relacional
- **Docker & Docker Compose**: Containerização da aplicação
- **Swagger/OpenAPI**: Documentação interativa da API
- **GitHub Actions**: Pipeline CI/CD automatizado
- **Testes de Integração**: Garantia de qualidade end-to-end

## 🛠️ Tecnologias

- Java 17+
- Spring Boot 3.2.0
- Maven 3.9
- PostgreSQL
- Docker
- GitHub Actions

## 📦 Estrutura do Projeto

```
.
├── .github/workflows/          # GitHub Actions workflows
│   ├── ci.yml                 # Continuous Integration
│   ├── cd_upload.yml          # Upload para Docker Hub
│   ├── release.yml            # Geração de releases
│   └── cd_integration_test.yml # Testes de integração
├── src/
│   ├── main/java/com/example/
│   │   ├── CheckpointApiApplication.java   # Classe principal
│   │   ├── model/Item.java                 # Entidade
│   │   ├── repository/ItemRepository.java  # Repositório
│   │   └── controller/ItemController.java  # Controller API
│   ├── main/resources/
│   │   └── application.properties          # Configuração
│   └── test/java/com/example/
│       └── ItemControllerIntegrationTest.java
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## 🚀 Executando a Aplicação

### Opção 1: Docker Compose (Recomendado)

A forma mais fácil de rodar a aplicação com o banco de dados:

```bash
# Na raiz do projeto
docker-compose up --build
```

A aplicação estará disponível em `http://localhost:8080`

### Opção 2: Imagem Docker do Docker Hub

Se a imagem já foi publicada no Docker Hub:

```bash
# Puxar a imagem
docker pull wesassis/checkpoint-api:latest

# Rodar o contêiner
docker run -d \
  --name checkpoint_api \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://<seu-banco>:5432/checkpoint_db \
  -e SPRING_DATASOURCE_USERNAME=checkpoint_user \
  -e SPRING_DATASOURCE_PASSWORD=checkpoint_password \
  wesassis/checkpoint-api:latest
```

### Opção 3: Maven (Desenvolvimento Local)

```bash
# Certifique-se de ter o PostgreSQL rodando localmente
mvn clean spring-boot:run
```

## 📚 Documentação da API

Após iniciar a aplicação, acesse a documentação interativa do Swagger:

**URL**: `http://localhost:8080/swagger-ui.html`

Todas as operações CRUD estão documentadas e podem ser testadas diretamente pela interface.

## 🔌 Endpoints da API

### Items (Itens)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/itens` | Criar um novo item |
| `GET` | `/api/itens` | Listar todos os itens |
| `GET` | `/api/itens/{id}` | Buscar item por ID |
| `PUT` | `/api/itens/{id}` | Atualizar um item |
| `DELETE` | `/api/itens/{id}` | Deletar um item |

### Exemplo de Requisição

#### Criar um item (POST)

```bash
curl -X POST http://localhost:8080/api/itens \
  -H "Content-Type: application/json" \
  -d '{"nome": "Primeiro Item", "descricao": "Descrição do item"}'
```

#### Listar todos os itens (GET)

```bash
curl http://localhost:8080/api/itens
```

#### Buscar item por ID (GET)

```bash
curl http://localhost:8080/api/itens/1
```

#### Atualizar item (PUT)

```bash
curl -X PUT http://localhost:8080/api/itens/1 \
  -H "Content-Type: application/json" \
  -d '{"nome": "Item Atualizado", "descricao": "Nova descrição"}'
```

#### Deletar item (DELETE)

```bash
curl -X DELETE http://localhost:8080/api/itens/1
```

## 🧪 Executando Testes

### Testes Unitários

```bash
mvn test
```

### Testes de Integração

```bash
mvn verify
```

### Testes de Integração com Docker

```bash
docker-compose up --build
mvn verify
docker-compose down
```

## 🔄 CI/CD Pipeline

### Workflows Disponíveis

1. **CI (ci.yml)**
   - Executado em: `push` nas branches `develop`, `feature`, `hotfix`
   - Tarefas: Testes unitários e empacotamento

2. **CD Upload (cd_upload.yml)**
   - Executado em: `pull_request` para `main`
   - Tarefas: Build Docker e upload para Docker Hub

3. **Release (release.yml)**
   - Executado em: `push` na branch `main`
   - Tarefas: Geração de tag e release notes

4. **CD Integration Tests (cd_integration_test.yml)**
   - Executado em: `push` nas branches `develop`, `main`
   - Tarefas: Testes de integração com Docker Compose

### Configuração de Secrets

Para que o pipeline de CI/CD funcione corretamente, configure os seguintes secrets no GitHub:

- `DOCKER_USERNAME`: Seu usuário do Docker Hub
- `DOCKER_PASSWORD`: Seu token de acesso do Docker Hub

**Como configurar:**
1. Vá para `Settings` > `Secrets and variables` > `Actions`
2. Clique em `New repository secret`
3. Adicione `DOCKER_USERNAME` e `DOCKER_PASSWORD`

## 📋 Variáveis de Ambiente

### Application.properties

```properties
spring.datasource.url=jdbc:postgresql://db:5432/checkpoint_db
spring.datasource.username=checkpoint_user
spring.datasource.password=checkpoint_password
spring.jpa.hibernate.ddl-auto=update
```

## 🐛 Troubleshooting

### Erro de conexão com banco de dados

**Solução**: Certifique-se de que:
- O serviço PostgreSQL está rodando
- As credenciais estão corretas
- O host está acessível (use `db` ao rodar com Docker Compose)

### Porta 8080 já em uso

```bash
# Verificar qual processo está usando a porta
lsof -i :8080

# Rodar em uma porta diferente
docker run -p 9000:8080 wesassis/checkpoint-api:latest
```

### Build Maven falha

```bash
# Limpar cache Maven
mvn clean

# Reconstruir
mvn package
```

## 📄 Licença

Este projeto é disponibilizado como está, para fins educacionais.

## 👤 Autor

Desenvolvido como parte do Checkpoint SOA - Semestre 2

---

**Última atualização**: Novembro de 2025
