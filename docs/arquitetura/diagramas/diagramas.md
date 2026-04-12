# 📊 Diagramas do Projeto — MeuBolso

---

# 📐 1. Diagrama de Contexto (C4 — Nível 1)

---

## 🎯 Objetivo

Mostrar o sistema como um todo e suas interações externas.

---

```mermaid
flowchart LR

User[Usuário]

Frontend[Frontend Angular]
Backend[Backend Spring Boot]
DB[(PostgreSQL)]

User --> Frontend
Frontend --> Backend
Backend --> DB
```

---

## 📌 Interpretação

* Usuário interage apenas com o frontend
* Frontend consome API REST
* Backend concentra toda lógica
* Banco armazena estado

---

# 🧩 2. Diagrama de Containers (C4 — Nível 2)

---

## 🎯 Objetivo

Detalhar os principais blocos executáveis do sistema.

---

```mermaid
flowchart LR

subgraph Frontend
  AngularApp[Angular 21 App]
end

subgraph Backend
  API[API REST]
  App[Application Layer]
  Domain[Domain Layer]
  Infra[Infrastructure Layer]
end

DB[(PostgreSQL 18)]

AngularApp --> API
API --> App
App --> Domain
App --> Infra
Infra --> DB
```

---

## 📌 Interpretação

* separação clara por camadas
* domínio isolado
* infraestrutura desacoplada

---

# 🧠 3. Diagrama de Contextos (DDD)

---

## 🎯 Objetivo

Mostrar os bounded contexts do sistema.

---

```mermaid
flowchart LR

Importacao --> Classificacao
Classificacao --> Financeiro

Financeiro --> Obrigacoes
Financeiro --> Recebiveis

Obrigacoes --> Financeiro
Recebiveis --> Financeiro
```

---

## 📌 Contextos

* Core Financeiro
* Gestão de Obrigações
* Gestão de Recebíveis
* Importação
* Classificação

---

---

# 🧱 4. Diagrama de Módulos do Backend

---

```mermaid
flowchart TB

subgraph Backend

CoreFinanceiro
GestaoObrigacoes
GestaoRecebiveis
Importacao
Classificacao
Shared

end

Importacao --> Classificacao
Classificacao --> CoreFinanceiro
GestaoObrigacoes --> CoreFinanceiro
GestaoRecebiveis --> CoreFinanceiro
```

---

---

# 🔄 5. Fluxo de Caixa (Visão funcional)

---

```mermaid
flowchart LR

ContaReceber -->|Recebimento| LancamentoReceita
ContaPagar -->|Pagamento| LancamentoDespesa
Divida -->|Parcela paga| LancamentoDespesa

LancamentoReceita --> Saldo
LancamentoDespesa --> Saldo
```

---

---

# 📥 6. Fluxo de Importação

---

```mermaid
flowchart LR

Arquivo[Arquivo/Extrato] --> Importacao
Importacao --> Staging[Transações Importadas]
Staging --> Classificacao
Classificacao --> Revisao
Revisao --> Lancamento
```

---

---

# 🧠 7. Fluxo de Classificação Inteligente

---

```mermaid
flowchart LR

Transacao --> Regra
Regra -->|match| Classificacao

Transacao --> Historico
Historico --> Classificacao

Classificacao --> Confiança
Confiança --> Usuario
```

---

---

# 🔐 8. Fluxo de Autenticação

---

```mermaid
sequenceDiagram

participant User
participant Frontend
participant Backend

User->>Frontend: login
Frontend->>Backend: email + senha
Backend->>Backend: valida
Backend-->>Frontend: JWT
Frontend->>Backend: request + token
Backend->>Backend: valida token
Backend-->>Frontend: resposta
```

---

---

# 🧾 9. Fluxo de Conta a Pagar

---

```mermaid
sequenceDiagram

participant User
participant Frontend
participant Backend

User->>Frontend: cria conta a pagar
Frontend->>Backend: dados
Backend->>Backend: valida
Backend-->>Frontend: salva

User->>Frontend: paga conta
Frontend->>Backend: pagar

Backend->>Backend: cria lançamento
Backend-->>Frontend: confirmado
```

---

---

# 📊 10. Fluxo de Conta a Receber

---

```mermaid
sequenceDiagram

participant User
participant Frontend
participant Backend

User->>Frontend: cria conta a receber
Frontend->>Backend: dados
Backend-->>Frontend: salva

User->>Frontend: confirma recebimento
Frontend->>Backend: receber

Backend->>Backend: cria lançamento
Backend-->>Frontend: confirmado
```

---

---

# 🗄️ 11. Diagrama de Entidades

---

```mermaid
classDiagram

class Conta
class Lancamento
class Categoria
class Parte

class ContaPagar
class ContaReceber
class Divida
class Parcela

Conta --> Lancamento
Lancamento --> Categoria
Lancamento --> Parte

ContaPagar --> Lancamento
ContaReceber --> Lancamento

Divida --> Parcela
Parcela --> Lancamento
```

---

---

# ⚙️ 12. Diagrama de Infraestrutura (Docker)

---

```mermaid
flowchart LR

FrontendContainer[Angular Container]
BackendContainer[Spring Container]
DBContainer[(Postgres Container)]

FrontendContainer --> BackendContainer
BackendContainer --> DBContainer
```

---