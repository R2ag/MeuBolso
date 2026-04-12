# 📄 ADR-009 — Separação entre Planejado e Realizado

---

## Status

Aceito

---

## Contexto

Sistemas financeiros precisam separar previsão de execução.

---

## Decisão

Adotar:

```text
ContaPagar / ContaReceber → planejado
Lancamento → realizado
```

---

## Consequências

### Positivas

* modelo financeiro correto
* permite projeção de fluxo de caixa

---

### Negativas

* maior complexidade de modelagem

---
