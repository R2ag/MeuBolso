# 📄 ADR-014 — Orquestração com Docker Compose

---

## Status

Aceito

---

## Contexto

O sistema é composto por múltiplos serviços (backend, frontend e banco).

---

## Decisão

Adotar:

```text
Docker Compose para orquestração local
```

---

## Consequências

### Positivas

* inicialização com um único comando
* fácil configuração de rede entre serviços
* ideal para desenvolvimento

---

### Negativas

* não indicado para produção em larga escala

---
