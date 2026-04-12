# 📄 ADR-016 — Separação de containers por serviço

---

## Status

Aceito

---

## Contexto

Cada componente do sistema possui responsabilidades distintas.

---

## Decisão

Executar cada componente em um container separado:

```text
- backend
- frontend
- banco de dados
```

---

## Consequências

### Positivas

* isolamento de responsabilidades
* facilidade de manutenção
* melhor escalabilidade futura

---

### Negativas

* necessidade de gerenciar comunicação entre containers

---
