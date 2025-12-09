# Tenda Challange

## 🛠️ Tecnologias Utilizadas

Este projeto foi desenvolvido utilizando:

- **Java 21** ☕
- **Spring Boot 3.5.8** 🚀
- **Spring Data JPA** 🔍
- **PostgreSQL** 🐘
- **Flyway** 💿
- **Swagger OpenAPI** 📜

---

## ⚙️ Configuração do Ambiente

### 🔹 Requisitos

- [JDK 21](https://www.oracle.com/br/java/technologies/downloads/#java21)
- [Maven 3.8+](https://maven.apache.org/)
- [PostgreSQL 14+](https://www.postgresql.org/)
- [Intellij IDEA](https://www.jetbrains.com/idea/) ou IDE de sua preferência
---

### 🏗️ Configuração do Banco de Dados

Crie um banco de dados no PostgreSQL e configure as credenciais no `application.yml` ou defina variáveis de ambiente:

```yml
    spring:
        datasource:
            url: ${POSTGRES_URL: SEU_BANCO}
            username: ${POSTGRES_USERNAME: SEU_ USER}
            password: ${POSTGRES_PASSWORD: SUA_SENHA}
```
---

## ▶️ Executando o Projeto

1. Clone o repositório:

```sh
git clone https://github.com/ferreira-eric/tenda-project.git
cd tenda-project
```

2. Instale as dependências e execute o projeto:

```sh
mvn spring-boot:run
```

3. O servidor será iniciado em `http://localhost:8080`

---

## 📜 Documentação da API

A API conta com documentação Swagger disponível em:

🔗 [Swagger UI](http://localhost:8080/swagger-ui/index.html#/)  
🔗 [OpenAPI JSON](http://localhost:8080/v3/api-docs)

---

## 📂 Estrutura do Projeto

```plaintext
/src
├── main
│   ├── java/com
│   │   ├── config/             # Configurações do projeto
│   │   ├── dto/               # Data Transfer Objects (DTOs)
│   │   ├── entity/         # Entidades
│   │   ├── exception/         # Tratamento de exceções
│   │   ├── repository/         # Camada de persistência
│   │   ├── rest/
│   │   │   ├── api/            # Endpoints da API
│   │   │   ├── controllers/    # Controladores REST
│   │   ├── service/            # Regras de negócio
│   │   ├── TendaChallengeApplication  # Classe principal
│   ├── resources/
│   │   ├── application.yml  # Configurações da aplicação
│   │   ├── db
│   │   │   ├── migrations/  # Migrations usando Flyway
├── test/                      
```

