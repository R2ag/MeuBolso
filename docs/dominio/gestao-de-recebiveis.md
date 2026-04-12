# 📥 Domínio de Gestão de Recebíveis

## 📌 Conceitos principais

### Conta a Receber

Representa um valor financeiro futuro que será recebido.

Exemplos:

* Salário
* Pagamento de cliente
* Reembolso
* Aluguel recebido

---

### Status da Conta

Define a situação atual do recebimento.

Tipos:

* Pendente → ainda não recebido
* Recebido → valor já entrou
* Atrasado → passou da data prevista
* Cancelado → não será mais recebido

---

### Recorrência

Indica que o recebimento se repete automaticamente ao longo do tempo.

Exemplos:

* Mensal (salário, aluguel)
* Semanal
* Anual

---

### Data de Emissão

Data em que o direito de receber foi gerado.

Exemplo:

* Serviço realizado no dia 01

---

### Data de Recebimento Previsto

Data esperada para entrada do dinheiro.

Exemplo:

* Cliente paga no dia 10

---

### Categoria

Classifica o tipo da receita.

Exemplos:

* Salário
* Prestação de serviço
* Investimentos

---

### Parte (Contraparte)

Representa quem irá pagar.

Exemplos:

* Empresa
* Cliente
* Inquilino
* Banco

---

## 📊 Regras importantes

---

* Conta a receber **não é uma receita realizada**
* Conta a receber **não impacta o saldo diretamente**
* O impacto financeiro ocorre **somente ao gerar um lançamento**

---

* Ao receber um valor:

  * deve ser criado um lançamento
  * o status deve mudar para **Recebido**

---

* Uma conta não pode ser recebida mais de uma vez

---

* Contas vencidas devem ser marcadas como **Atrasadas**

---

* Contas recorrentes devem gerar novos recebimentos automaticamente

---

* Após o recebimento:

  * o valor não pode ser alterado
  * a categoria não pode ser alterada

---

* Toda conta a receber deve possuir uma categoria

---

## 🧠 Relação com outros domínios

---

* Conta a receber → gera → Lançamento
* Conta a receber → influencia → Orçamento (previsto)
* Lançamento → representa → realizado

---

## 🎯 Objetivo do domínio

---

Permitir:

* controle de receitas futuras
* previsibilidade de entradas financeiras
* acompanhamento de inadimplência
* base para projeção de fluxo de caixa
