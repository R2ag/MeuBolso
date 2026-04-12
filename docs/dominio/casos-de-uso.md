# Casos de Uso do Sistema MeuBolso

---

## Objetivo

Descrever os comportamentos do sistema sob a perspectiva do usuário, garantindo alinhamento entre:

- Regras de negócio
- Implementação técnica
- Experiência do usuário

---

## Atores

- Usuário (principal)
- Sistema (processos automáticos)
- Integrações externas (futuro)

---

# 1. Gestão de Lançamentos

## UC-001: Criar lançamento manual

### Descrição

Registrar uma receita ou despesa manualmente.

### Fluxo principal

```
1. Usuário acessa tela de lançamentos
2. Seleciona "Novo lançamento"
3. Informa:
   - descrição
   - valor
   - data
   - conta
   - categoria
   - contraparte (opcional)
4. Sistema valida dados
5. Sistema cria lançamento com status = CONFIRMADO
```

### Regras

- Valor ≠ 0
- Categoria obrigatória (exceto transferência)
- Conta obrigatória
- Tipo definido automaticamente pelo valor

---

## UC-002: Editar lançamento

### Fluxo

```
1. Usuário seleciona lançamento
2. Altera dados permitidos
3. Sistema valida
4. Sistema salva alterações
```

### Regras

- Após CONFIRMADO:
  - valor não pode ser alterado
  - conta não pode ser alterada

---

## UC-003: Excluir lançamento

### Fluxo

```
1. Usuário solicita exclusão
2. Sistema valida
3. Sistema remove lançamento
```

### Regras

- Não permitir exclusão se vinculado a parcela ou conta a pagar

---

# 2. Importação de Transações

## UC-004: Importar transações

### Fluxo principal

```
1. Usuário envia arquivo (CSV/OFX)
2. Sistema cria Importacao
3. Sistema realiza parsing
4. Sistema normaliza dados
5. Sistema realiza deduplicação
6. Sistema armazena transações no staging
7. Sistema classifica automaticamente
8. Sistema apresenta preview
```

### Regras

- Não criar lançamentos automaticamente
- Toda transação passa pelo staging
- Duplicatas devem ser ignoradas

---

## UC-005: Confirmar importação

### Fluxo

```
1. Usuário revisa transações
2. Ajusta categoria/parte
3. Confirma importação
4. Sistema cria lançamentos
5. Marca como processadas
```

---

# 3. Classificação Inteligente

## UC-006: Classificar transação automaticamente

### Fluxo

```
1. Sistema recebe transação
2. Aplica regras
3. Consulta histórico
4. Aplica similaridade
5. Calcula score
6. Retorna sugestão
```

### Regras

- Prioridade: regra > histórico > similaridade
- Deve retornar confiança

---

## UC-007: Aprender com correção

### Fluxo

```
1. Usuário corrige classificação
2. Sistema registra
3. Atualiza histórico
4. Aumenta frequência
```

---

# 4. Orçamento

## UC-008: Definir orçamento

### Fluxo

```
1. Usuário define mês
2. Define valores por categoria
3. Sistema salva
```

### Regras

- Não permitir duplicidade (mês + categoria)

---

## UC-009: Acompanhar orçamento

### Fluxo

```
1. Sistema calcula:
   - realizado (lançamentos)
   - previsto (contas a pagar)
2. Compara com planejado
3. Exibe percentual
```

---

# 5. Gestão de Dívidas

## UC-010: Criar dívida

### Fluxo

```
1. Usuário informa:
   - valor total
   - parcelas
   - juros
2. Sistema gera parcelas
```

---

## UC-011: Pagar parcela

### Fluxo

```
1. Usuário seleciona parcela
2. Confirma pagamento
3. Sistema cria lançamento
4. Atualiza parcela
```

### Regras

- Toda parcela paga gera lançamento

---

# 6. Gestão de Contas a Pagar

## UC-012: Criar conta a pagar

### Fluxo

```
1. Usuário cria conta
2. Informa dados
3. Sistema valida
4. Cria ContaPagar (PENDENTE)
```

### Regras

- Categoria obrigatória
- Não gera lançamento automaticamente

---

## UC-013: Pagar conta a pagar

### Fluxo

```
1. Usuário seleciona conta
2. Informa pagamento
3. Sistema cria lançamento
4. Atualiza status = PAGO
```

### Regras

- Deve gerar exatamente um lançamento
- Não pode ser paga duas vezes

---

## UC-014: Atualizar status de atraso

### Fluxo

```
1. Sistema verifica vencimento
2. Atualiza status para ATRASADO
```

---

## UC-015: Gerar recorrência

### Fluxo

```
1. Sistema identifica contas recorrentes
2. Gera nova conta
3. Ajusta datas
```

---

## UC-016: Visualizar contas a pagar

### Fluxo

```
1. Usuário acessa tela
2. Sistema lista contas
3. Permite filtros
```

---

# 7. Gestão de Contas a Receber

## UC-017: Criar conta a receber

### Descrição

Registrar um valor futuro a ser recebido.

### Fluxo

```
1. Usuário cria registro
2. Informa:
   - descrição
   - valor
   - data emissão
   - data recebimento prevista
   - categoria
   - contraparte
   - recorrente (opcional)
3. Sistema valida
4. Cria ContaReceber (PENDENTE)
```

### Regras

- Valor > 0
- Categoria obrigatória
- Não gera lançamento automaticamente

---

## UC-018: Registrar recebimento

### Fluxo

```
1. Usuário seleciona ContaReceber
2. Informa:
   - conta destino
   - data recebimento
3. Sistema cria lançamento (RECEITA)
4. Vincula lancamentoId
5. Atualiza status = RECEBIDO
```

### Regras

- Deve gerar exatamente um lançamento
- Não pode receber duas vezes
- Após recebido: valor não pode ser alterado

---

## UC-019: Atualizar status de atraso

### Fluxo

```
Se data prevista < hoje e não recebido:
  status = ATRASADO
```

---

## UC-020: Gerar recorrência de receitas

### Fluxo

```
1. Sistema identifica recorrentes
2. Gera novos registros
3. Ajusta datas
```

---

## UC-021: Listar contas a receber

### Fluxo

```
1. Usuário acessa tela
2. Sistema lista
3. Permite filtros:
   - status
   - período
   - categoria
```

---

## UC-022: Visualizar previsão de receitas

### Fluxo

```
1. Sistema busca pendentes
2. Agrupa por período
3. Calcula total previsto
```

---

# 8. Gestão de Contas

## UC-023: Criar conta

### Fluxo

```
1. Usuário informa dados
2. Sistema cria conta
```

---

## UC-024: Transferência entre contas

### Fluxo

```
1. Usuário define origem/destino
2. Informa valor
3. Sistema cria transferência
```

### Regras

- Não impacta receita/despesa

---

# 9. Dashboard e Análises

## UC-025: Visualizar resumo financeiro

### Fluxo

```
1. Sistema calcula:
   - saldo atual
   - saldo projetado (contas a pagar)
2. Exibe dados
```

---

## UC-026: Visualizar gastos por categoria

### Fluxo

```
1. Sistema agrupa lançamentos
2. Calcula totais
3. Exibe gráfico
```

---

# 10. Casos Técnicos

## UC-027: Deduplicar transações

### Fluxo

```
1. Gerar hash
2. Verificar existência
3. Ignorar duplicatas
```

---

## UC-028: Normalizar descrição

### Fluxo

```
1. Limpar texto
2. Remover padrões
3. Padronizar
```

---

# 11. Regras Globais

- ContaPagar NÃO impacta saldo diretamente
- ContaReceber NÃO impacta saldo diretamente
- Apenas Lancamento impacta saldo
- Toda ContaPagar paga gera lançamento
- Toda ContaReceber recebida gera lançamento
- Toda parcela paga gera lançamento
- Toda despesa deve ter categoria