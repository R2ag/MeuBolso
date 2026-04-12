# 🏗️ Infraestrutura — MeuBolso

---

## 🎯 Objetivo

Definir a infraestrutura do sistema para:

* desenvolvimento local padronizado
* facilidade de setup
* isolamento de serviços
* preparação para ambientes futuros (staging/prod)

---

# 📐 1. Visão geral

---

A infraestrutura será baseada em:

```text
Docker + Docker Compose
```

---

## 📦 Serviços iniciais

```text
- backend (Spring Boot)
- frontend (Angular)
- banco de dados (PostgreSQL)
```

---

## 🧭 Arquitetura

```text
Frontend (Angular)
        ↓
Backend (Spring)
        ↓
PostgreSQL
```

---

# 📁 2. Estrutura de diretórios

---

```text
infra/
├── docker/
│   ├── backend/
│   │   └── Dockerfile
│   │
│   ├── frontend/
│   │   └── Dockerfile
│   │
│   └── db/
│       └── init.sql
│
└── docker-compose.yml
```

---

# 🐳 3. Serviços Docker

---

## 🟦 Backend (Spring Boot)

---

### 📌 Dockerfile

```dockerfile
FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY target/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

### 📌 Configuração

* Porta: `8080`
* Perfil: `dev`
* Java: `25` via imagem `eclipse-temurin:25-jdk`

---

---

## 🟨 Frontend (Angular)

---

### 📌 Dockerfile (dev)

```dockerfile
FROM node:24

WORKDIR /app

COPY . .

RUN npm install

EXPOSE 4200

CMD ["npm", "run", "start"]
```

---

### 📌 Configuração

* Porta: `4200`
* Hot reload habilitado

---

---

## 🟩 Banco de Dados (PostgreSQL)

---

### 📌 Imagem

```text
postgres:18
```

---

### 📌 Configuração

```text
Database: meubolso
User: meubolso
Password: meubolso
```

---

### 📌 Volume

* persistência de dados

---

---

# 🔗 4. Docker Compose

---

## 📄 `docker-compose.yml`

```yaml
version: '3.9'

services:

  db:
    image: postgres:18
    container_name: meubolso-db
    restart: always
    environment:
      POSTGRES_DB: meubolso
      POSTGRES_USER: meubolso
      POSTGRES_PASSWORD: meubolso
    ports:
      - "5432:5432"
    volumes:
      - db_data:/var/lib/postgresql/data

  backend:
    build: ./docker/backend
    container_name: meubolso-backend
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/meubolso
      SPRING_DATASOURCE_USERNAME: meubolso
      SPRING_DATASOURCE_PASSWORD: meubolso
    ports:
      - "8080:8080"

  frontend:
    build: ./docker/frontend
    container_name: meubolso-frontend
    depends_on:
      - backend
    ports:
      - "4200:4200"

volumes:
  db_data:
```

---

# 5. Configuração do Backend

---

## 📄 `application-dev.yml`

```yaml
spring:
  datasource:
    url: jdbc:postgresql://db:5432/meubolso
    username: meubolso
    password: meubolso

  jpa:
    hibernate:
      ddl-auto: update
```

---

# 6. Variáveis de ambiente

---

## 📌 Estratégia

* usar `.env` no futuro
* não versionar credenciais reais

---

## 📌 Exemplos

```text
DB_USER=meubolso
DB_PASSWORD=meubolso
JWT_SECRET=changeme
```

---

# 🔄 7. Fluxo de execução

---

## 🚀 Subir ambiente

```bash
docker-compose up --build
```

---

## 🌐 Acessos

```text
Frontend: http://localhost:4200
Backend:  http://localhost:8080
DB:       localhost:5432
```

---

# 📊 8. Persistência de dados

---

* volume Docker para banco
* dados não são perdidos ao reiniciar containers

---

# 🧪 9. Ambiente de desenvolvimento

---

## 📌 Backend

* build via Maven
* hot reload (futuro com devtools)

---

## 📌 Frontend

* hot reload ativo
* execução via Node

---

# 🚀 10. Evolução futura

---

## 🔹 Curto prazo

* `.env` para configuração
* scripts de inicialização

---

## 🔹 Médio prazo

* ambiente de staging
* CI/CD
* build otimizado

---

## 🔹 Longo prazo

* Kubernetes
* deploy em cloud
* escalabilidade horizontal

---

# 11. Boas práticas

---

* nunca versionar segredos
* usar volumes para dados persistentes
* separar ambientes (dev/staging/prod)
* manter imagens leves

---
