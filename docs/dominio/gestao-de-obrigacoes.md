# 📄 Domínio de Gestão de Obrigações

## 📌 Conceitos principais

### Conta a Pagar

Representa uma obrigação financeira futura, onde há um valor a ser pago.

Exemplos:

* Conta de internet
* Aluguel
* Energia elétrica
* Assinaturas

---

### Status da Conta

Define a situação atual da obrigação.

Tipos:

* Pendente → ainda não paga
* Pago → já foi quitada
* Atrasado → passou do vencimento
* Cancelado → não será mais paga

---

### Recorrência

Indica que a conta se repete automaticamente ao longo do tempo.

Exemplos:

* Mensal (internet, aluguel)
* Semanal
* Anual

---

### Data de Emissão

Data em que a obrigação foi gerada.

Exemplo:

* Fatura criada no dia 01

---

### Data de Vencimento

Data limite para pagamento da conta.

Exemplo:

* Vencimento no dia 10

---

### Categoria

Classifica o tipo da despesa.

Exemplos:

* Moradia
* Internet
* Transporte

---

### Parte (Contraparte)

Representa quem está cobrando a obrigação.

Exemplos:

* Provedor de internet
* Imobiliária
* Concessionária

---

## 📊 Regras importantes

---

* Conta a pagar **não é uma despesa realizada**
* Conta a pagar **não impacta o saldo diretamente**
* O impacto financeiro ocorre **somente ao gerar um lançamento**

---

* Ao pagar uma conta:

  * deve ser criado um lançamento
  * o status deve mudar para **Pago**

---

* Uma conta não pode ser paga mais de uma vez

---

* Contas vencidas devem ser marcadas como **Atrasadas**

---

* Contas recorrentes devem gerar novas contas automaticamente

---

* Após o pagamento:

  * o valor não pode ser alterado
  * a categoria não pode ser alterada

---

* Toda conta a pagar deve possuir uma categoria

---

## 🧠 Relação com outros domínios

---

* Conta a pagar → gera → Lançamento
* Conta a pagar → influencia → Orçamento (previsto)
* Lançamento → representa → realizado

---

## 🎯 Objetivo do domínio

---

Permitir:

* controle de despesas futuras
* organização de contas recorrentes
* previsibilidade financeira
* base para projeção de fluxo de caixa

---