# 📄 ADR-006 — Controle de Acesso por Usuário (Multi-tenant simples)

---

## Status

Aceito

---

## Contexto

Cada usuário possui dados financeiros isolados.

---

## Decisão

Adotar:

```text
Isolamento por userId em todas as entidades
```

---

## Consequências

### Positivas

* segurança de dados
* simplicidade de implementação

---

### Negativas

* necessidade de garantir filtros em todas as queries

---