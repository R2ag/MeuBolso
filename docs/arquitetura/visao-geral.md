# 🏗️ Arquitetura do Sistema — MeuBolso

---

## 🎯 Objetivo

Definir a estrutura arquitetural do sistema, garantindo:

* separação de responsabilidades
* escalabilidade
* manutenibilidade
* alinhamento com o domínio

---

# 📐 1. Visão geral da arquitetura

---

O sistema será estruturado em:

```text
Frontend (Angular)
        ↓
Backend (Spring Boot)
        ↓
Banco de Dados
```

---

## 🧠 Estilo arquitetural

---

O backend seguirá:

```text
Arquitetura em Camadas + DDD (Domain-Driven Design)
```

---

### Camadas principais

```text
- API (Controllers)
- Application (Use Cases)
- Domain (Regras de negócio)
- Infrastructure (Persistência e integrações)
```

---

# 🧩 2. Organização do Monorepo

---

```text
meubolso/
│
├── backend/
│   ├── core-financeiro/
│   ├── gestao-obrigacoes/
│   ├── gestao-recebiveis/
│   ├── importacao/
│   ├── classificacao/
│   └── shared/
│
├── frontend/
│   └── angular-app/
│
├── docs/
│   ├── api/
│   ├── arquitetura/
│   ├── decisoes/
│   ├── dominio/
│   └── produto/
│
└── infra/
    ├── docker/
    └── database/
```

---

# 🧱 3. Backend (Spring Boot)

---

## 📌 Organização por contexto (DDD)

Cada contexto será um módulo isolado:

```text
gestao-obrigacoes/
gestao-recebiveis/
core-financeiro/
importacao/
classificacao/
```

---

## 📦 Estrutura interna de cada módulo

```text
modulo/
├── domain/
│   ├── model/
│   ├── repository/
│   └── service/
│
├── application/
│   ├── usecase/
│   └── dto/
│
├── infrastructure/
│   ├── persistence/
│   └── config/
│
└── api/
    └── controller/
```

---

## 🧠 Responsabilidade de cada camada

---

### Domain

* entidades
* regras de negócio
* invariantes

👉 NÃO depende de nada externo

---

### Application

* orquestra casos de uso
* coordena domínio + infraestrutura

👉 não contém regra de negócio complexa

---

### Infrastructure

* banco de dados
* integrações externas
* implementação de repositórios

---

### API

* entrada HTTP
* validação
* transformação DTO ↔ domínio

---

# 🗄️ 4. Banco de Dados

---

## 📌 Estratégia

```text
Banco relacional (PostgreSQL)
```

---

## 📌 Princípios

* Normalização
* Integridade referencial
* Auditoria (futuro)

---

## 📌 Separação lógica

Tabelas organizadas por contexto:

```text
financeiro_*
obrigacoes_*
recebiveis_*
importacao_*
```

---

# 🔗 5. Comunicação entre módulos

---

## 📌 Estratégia inicial

```text
Comunicação direta (in-process)
```

---

## 📌 Evolução futura

```text
Eventos de domínio (event-driven)
```

---

# 📡 6. Eventos (preparação)

---

Exemplos:

```text
ContaPagarPaga → cria Lancamento
ContaReceberRecebida → cria Lancamento
TransacaoImportada → classificar
```

---

# 🔐 7. Segurança (futuro)

---

* Autenticação via JWT
* Controle por usuário

---

# 8. Frontend (Angular)

---

## 📌 Estrutura

```text
app/
├── core/
├── shared/
├── features/
│   ├── financeiro/
│   ├── obrigacoes/
│   ├── recebiveis/
│   └── dashboard/
```

---

## 📌 Responsabilidades

* consumo da API
* gerenciamento de estado
* UI/UX

---

# 🔄 9. Fluxo típico

---

## 📄 Exemplo: pagar conta

```text
Frontend → API → UseCase → Domain → Repository
                                 ↓
                           Banco de Dados
                                 ↓
                       Gera Lancamento
```

---

#  10. Princípios arquiteturais

---

## 📌 Separação de responsabilidades

* domínio não conhece infraestrutura
* API não contém regra de negócio

---

## 📌 Fonte da verdade

```text
Lancamento = única fonte do saldo
```

---

## 📌 Independência de módulos

* cada contexto evolui isoladamente

---

# 🚀 11. Evolução futura

---

## 🔹 Curto prazo

* implementação modular (monolito modular)

---

## 🔹 Médio prazo

* eventos internos
* filas

---

## 🔹 Longo prazo

* microserviços (se necessário)

---

# 🧭 12. Decisões arquiteturais (ADR)

---

Você deve registrar decisões como:

```text
- uso de monorepo
- uso de DDD
- separação por contexto
- uso de PostgreSQL
```

---

# 🎯 Conclusão

Essa arquitetura garante:

---

✅ alinhamento com o domínio
✅ escalabilidade controlada
✅ facilidade de manutenção
✅ base sólida para crescimento

---

# 🚀 Próximo passo (recomendado)

Agora você tem:

✔ Domínio
✔ Casos de uso
✔ Arquitetura

---

👉 Próximo passo ideal:

### 🔥 Criar modelo de dados (SQL + JPA)

ou

### 🔥 Implementar primeiro módulo (Gestão de Obrigações)

---

Se quiser, posso seguir com:

✅ Estrutura inicial do projeto Spring (código real)
✅ Entidades JPA completas
✅ Primeiro caso de uso implementado

Só me fala 👇
