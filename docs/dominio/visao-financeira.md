# 📊 Visão Financeira — Fluxo de Caixa Completo

---

## 🎯 Objetivo

Fornecer uma visão consolidada da situação financeira, incluindo:

* saldo atual
* entradas futuras
* saídas futuras
* projeção de saldo

---

# 📌 Conceitos principais

---

## 💰 Saldo Atual

Representa o dinheiro disponível no momento.

✔ Baseado exclusivamente em lançamentos realizados

```text
Saldo Atual = soma dos lançamentos confirmados
```

---

## 📤 Saídas Futuras (Contas a Pagar)

Representam valores que ainda serão pagos.

Exemplos:

* contas mensais
* assinaturas
* despesas previstas

---

## 📥 Entradas Futuras (Contas a Receber)

Representam valores que ainda serão recebidos.

Exemplos:

* salário
* pagamentos de clientes
* reembolsos

---

## 📈 Saldo Projetado

Representa o saldo esperado considerando o futuro.

```text
Saldo Projetado = Saldo Atual
                + Entradas Futuras
                - Saídas Futuras
```

---

# 🧠 Estrutura do fluxo de caixa

---

## 🔄 Linha do tempo financeira

```text
Passado        Presente        Futuro
--------|----------------|----------------
        ↑                ↑
   realizados       projeções
```

---

## 📊 Componentes

---

### ✔ Realizado

* Lançamentos confirmados
* Impacta saldo diretamente

---

### ✔ Previsto

* Contas a pagar
* Contas a receber
* Não impacta saldo diretamente

---

# 📊 Tipos de visão

---

## 📌 1. Visão Atual

```text
Saldo Atual
Receitas realizadas
Despesas realizadas
```

---

## 📌 2. Visão Projetada

```text
Saldo Atual
+ Entradas futuras
- Saídas futuras
= Saldo Projetado
```

---

## 📌 3. Visão por período

Permite analisar por:

* dia
* semana
* mês

---

### Exemplo

```text
Abril:

Entradas:
+ 3000 (salário)

Saídas:
- 700 (aluguel)
- 150 (internet)

Saldo projetado: +2150
```

---

# 📊 Regras importantes

---

* Apenas lançamentos impactam saldo atual

---

* Contas a pagar representam **saídas futuras**, não despesas realizadas

---

* Contas a receber representam **entradas futuras**, não receitas realizadas

---

* Todo pagamento ou recebimento deve gerar um lançamento

---

* Não pode existir divergência entre:

```text
Lançamentos ↔ saldo
```

---

# 🔗 Integração entre domínios

---

```text
ContaPagar   → pagamento → Lancamento (despesa)
ContaReceber → recebimento → Lancamento (receita)
Divida       → parcela paga → Lancamento
```

---

# 📉 Análises possíveis

---

## 📊 1. Previsão de caixa

Saber se você terá dinheiro suficiente no futuro

---

## 📊 2. Identificação de risco

```text
Se saldo projetado < 0 → risco financeiro
```

---

## 📊 3. Controle de inadimplência

* contas a receber atrasadas
* contas a pagar vencidas

---

## 📊 4. Planejamento financeiro

* ajustar gastos
* antecipar receitas
* renegociar dívidas

---

# Regras críticas

---

```text
- Misturar previsto com realizado quebra o sistema
- Lançamento é a única fonte da verdade do saldo
- Contas (pagar/receber) são apenas projeções
```

---

# 🧭 Evolução futura

---

Esse modelo permite evoluir para:

---

## 🔮 Funcionalidades avançadas

* projeção de fluxo de caixa por meses
* alertas de saldo negativo
* simulação de cenários
* recomendação financeira automática

---

## 🤖 Inteligência

* previsão baseada em histórico
* detecção de padrões de gasto
* sugestão de economia

---