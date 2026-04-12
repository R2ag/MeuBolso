# 📋 Backlog de Desenvolvimento — MeuBolso

## Objetivo

Transformar o roadmap e as decisões arquiteturais em um backlog técnico alinhado ao monorepo, aos contextos DDD e às ADRs do projeto.

---

## Épico 1 — Estrutura do Projeto e Fundamentos do Backend

### Descrição
Estabelecer a base do monorepo e a arquitetura do backend, garantindo DDD, camadas e isolamento por contexto.

### Itens
- Configurar monorepo com módulos:
  - `core-financeiro`
  - `gestao-obrigacoes`
  - `gestao-recebiveis`
  - `importacao`
  - `classificacao`
  - `shared`
- Criar template de módulo com camadas:
  - `api`
  - `application`
  - `domain`
  - `infrastructure`
- Definir contratos de DTOs e APIs iniciais
- Implementar modelo básico de segurança JWT e `userId` em entidades (ADR-005 e ADR-006)
- Configurar build do backend para Java 25 / Spring Boot 4.0.5 (ADR-024 e ADR-025)

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
- Modelar entidade `Lancamento`
- Modelar entidades `Conta` e `Categoria`
- Implementar casos de uso:
  - criar lançamento
  - editar lançamento
  - excluir lançamento
  - listar lançamentos
- Aplicar regras de negócio:
  - valor ≠ 0
  - categoria obrigatória
  - conta obrigatória
  - tipo definido por valor
  - edição restrita para lançamentos confirmados
- Criar controllers REST e DTOs de entrada/saída
- Implementar filtros por `userId` em todas as queries

### Critérios de aceitação
- CRUD de lançamentos testado
- Entradas inválidas são rejeitadas
- Dados são persistidos no PostgreSQL 18 (ADR-004 e ADR-015)

---

## Épico 3 — Importação de Transações com Staging

### Descrição
Construir pipeline de importação com staging obrigatório antes de criar lançamentos, conforme ADR-011.

### Itens
- Implementar upload de arquivo CSV/OFX
- Criar entidade `Importacao` e `TransacaoStaging`
- Implementar parsing e normalização de transações
- Realizar deduplicação de transações
- Construir fluxo:
  - arquivo → importacao → staging → classificacao → revisao → lancamento
- Exibir preview de transações em staging
- Confirmar importação para gerar lançamentos

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
  - definir orçamento por mês/categoria
  - acompanhar orçamento realizado vs planejado
  - criar conta a pagar
  - criar conta a receber
  - criar dívida e gerar parcelas
  - pagar parcela e gerar lançamento
- Estabelecer lógica de projeção de saldo:
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
  - regras explícitas
  - histórico de classificações
  - similaridade de descrição/parte
- Calcular score de confiança para sugestões
- Adicionar correção manual e aprendizado contínuo
- Integrar com fluxo de importação e revisão

### Critérios de aceitação
- Transações recebidas recebem sugestão de categoria
- Correções atualizam histórico
- Sugestões melhoram para transações semelhantes

---

## Épico 6 — Frontend Angular 21 e UX inicial

### Descrição
Construir a primeira camada de interface usando Angular 21 e PrimeNG, com foco em produtividade e consistência visual.

### Itens
- Configurar aplicação Angular 21
- Adotar PrimeNG + design tokens (ADR-007 e ADR-008)
- Implementar telas básicas:
  - login/autenticação
  - lançamentos
  - importação
  - orçamento
  - dívidas
- Rodar frontend em modo dev com hot reload (ADR-021)

### Critérios de aceitação
- Frontend comunica com backend
- Componentes PrimeNG usados nas telas
- Hot reload funcionando

---

## Épico 7 — Infraestrutura Local com Docker Compose

### Descrição
Garantir ambiente local padronizado com os três containers principais e configuração via variáveis de ambiente.

### Itens
- Criar `docker-compose.yml` para:
  - backend
  - frontend
  - PostgreSQL 18
- Usar volumes para persistência de dados (ADR-017)
- Conectar serviços pela rede interna Docker Compose (ADR-022)
- Configurar variáveis de ambiente para banco e perfis (ADR-018 e ADR-019)
- Gerar JAR do backend e executar no container (ADR-020)

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
- Criar testes de integração para fluxo de importação e lançamentos
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
