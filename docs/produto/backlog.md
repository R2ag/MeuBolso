# 📋 Backlog de Desenvolvimento — MeuBolso

## Objetivo

Transformar o roadmap e as decisões arquiteturais em um backlog técnico alinhado ao monorepo, aos contextos DDD e às ADRs do projeto.

---

## Épico 1 — Estrutura do Projeto e Fundamentos do Backend

### Descrição
Estabelecer a base do monorepo e a arquitetura do backend, garantindo DDD, camadas e isolamento por contexto.

### Itens
- [x] Configurar monorepo com módulos:
  - [x] `core-financeiro`
  - [x] `gestao-obrigacoes`
  - [x] `gestao-recebiveis`
  - [x] `importacao`
  - [x] `classificacao`
  - [x] `shared`
- [x] Criar template de módulo com camadas:
  - [x] `api`
  - [x] `application`
  - [x] `domain`
  - [x] `infrastructure`
- [x] Definir contratos de DTOs e APIs iniciais
- [x] Implementar modelo básico de segurança JWT e `userId` em entidades (ADR-005 e ADR-006)
- [x] Configurar build do backend para Java 25 / Spring Boot 4.0.5 (ADR-024 e ADR-025)

### Critérios de aceitação
- Projeto compila com sucesso em Java 25
- Estrutura de módulos criada
- API básica rodando localmente
- Autenticação JWT funcionando

---

## Épico 2 — CRUD de Lançamentos, Contas e Categorias

### Descrição
Implementar o domínio financeiro essencial para registrar receitas e despesas, incluindo as regras de domínio e a interface básica.

### Itens
- [x] Modelar entidade `Lancamento`
- [x] Modelar entidades `Conta` e `Categoria`
- [x] Implementar casos de uso:
  - [x] criar lançamento
  - [x] editar lançamento
  - [x] excluir lançamento
  - [x] listar lançamentos
- [x] Aplicar regras de negócio:
  - [x] valor ≠ 0
  - [x] categoria obrigatória
  - [x] conta obrigatória
  - [x] tipo definido por valor
  - [x] edição restrita para lançamentos confirmados
- [x] Criar controllers REST e DTOs de entrada/saída
- [x] Implementar filtros por `userId` em todas as queries
- [x] Adicionar testes de validação de domínio e persistência

### Critérios de aceitação
- CRUD de lançamentos testado
- Entradas inválidas são rejeitadas
- Dados são persistidos no PostgreSQL 18 (ADR-004 e ADR-015)

---

## Épico 3 — Importação de Transações com Staging

### Descrição
Construir pipeline de importação com staging obrigatório antes de criar lançamentos, conforme ADR-011.

### Itens
- [x] Implementar upload de arquivo CSV/OFX
- [x] Criar entidade `Importacao` e `TransacaoStaging`
- [x] Implementar parsing e normalização de transações
- [x] Realizar deduplicação de transações
- [x] Construir fluxo:
  - arquivo → importacao → staging → classificacao → revisao → lancamento
- [x] Exibir preview de transações em staging
- [x] Confirmar importação para gerar lançamentos

### Critérios de aceitação
- Importação salva no staging
- Transações não viram lançamentos automaticamente
- Duplicatas são ignoradas
- Confirmação cria lançamentos corretamente

---

## Épico 4 — Planejamento Financeiro e Gestão de Dívidas

### Descrição
Implementar orçamento e dívidas, mantendo a separação entre planejado e realizado (ADR-009).

### Itens
- Criar entidades `Orcamento`, `ContaPagar`, `ContaReceber`, `Divida`, `Parcela`
- Implementar casos de uso:
  - [x] definir orçamento por mês/categoria
  - [x] acompanhar orçamento realizado vs planejado
  - [x] criar conta a pagar
  - [x] criar conta a receber
  - [x] listar contas a pagar/receber
  - [x] criar dívida e gerar parcelas
  - [x] pagar parcela e gerar lançamento
- [x] Estabelecer lógica de projeção de saldo:
  - saldo atual + entradas futuras - saídas futuras

### Critérios de aceitação
- Orçamento mensal salvo e calculado
- Contas a pagar/receber armazenadas sem virar lançamento
- Pagamento de parcela gera lançamento validado

---

## Épico 5 — Classificação Inteligente

### Descrição
Implementar classificação automática com regras, histórico e similaridade, seguindo ADR-012.

### Itens
- Criar motor de classificação com:
  - [x] regras explícitas
  - [x] histórico de classificações
  - [x] similaridade de descrição/parte
- [x] Calcular score de confiança para sugestões
- [x] Adicionar correção manual e aprendizado contínuo
- [x] Integrar com fluxo de importação e revisão

### Critérios de aceitação
- Transações recebidas recebem sugestão de categoria
- Correções atualizam histórico
- Sugestões melhoram para transações semelhantes

---

## Épico 6 — Frontend Angular 21 e UX inicial

### Descrição
Construir a primeira camada de interface usando Angular 21 e PrimeNG, com foco em produtividade e consistência visual.

### Itens
- [x] Configurar aplicação Angular 21
- [x] Adotar PrimeNG + design tokens (ADR-007 e ADR-008)
- [x] Implementar telas básicas:
  - [x] login/autenticação
  - [x] lançamentos
  - [x] importação
  - [x] orçamento
  - [x] dívidas
- [x] Rodar frontend em modo dev com hot reload (ADR-021)

### Critérios de aceitação
- Frontend comunica com backend
- Componentes PrimeNG usados nas telas
- Hot reload funcionando

---

## Épico 7 — Infraestrutura Local com Docker Compose

### Descrição
Garantir ambiente local padronizado com os três containers principais e configuração via variáveis de ambiente.

### Itens
- [x] Criar `docker-compose.yml` para:
  - backend
  - frontend
  - PostgreSQL 18
- [x] Usar volumes para persistência de dados (ADR-017)
- [x] Conectar serviços pela rede interna Docker Compose (ADR-022)
- [x] Configurar variáveis de ambiente para banco e perfis (ADR-018 e ADR-019)
- [x] Gerar JAR do backend e executar no container (ADR-020)

### Critérios de aceitação
- `docker compose up` inicia todos os serviços
- Backend e frontend acessíveis localmente
- Dados persistem após reinício

---

## Épico 8 — Validação e Qualidade

### Descrição
Adicionar testes e validações para garantir estabilidade e aderência ao modelo financeiro.

### Itens
- Implementar testes unitários para casos de uso backend
- [x] Criar testes de integração para fluxo de importação e lançamentos
- Validar regras de domínio e filtros de `userId`
- Revisar compatibilidade com Java 25, Spring Boot 4 e PostgreSQL 18

### Critérios de aceitação
- Testes passam localmente
- Regras-chave cobertas por testes
- Build do backend aprovado

---

## Como usar este backlog

- Priorizar os épicos sequencialmente: 1 → 8
- Validar cada fase com critérios de aceitação
- Atualizar o documento conforme o projeto avançar
- Mapear tarefas para issues ou sprints
