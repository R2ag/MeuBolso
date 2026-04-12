# 📄 ADR-022 — Rede interna Docker

---

## Status

Aceito

---

## Contexto

Os serviços precisam se comunicar de forma isolada e segura.

---

## Decisão

Utilizar:

```text
Rede interna do Docker Compose
```

---

## Consequências

### Positivas

* comunicação simplificada (via nome do serviço)
* isolamento do ambiente

---

### Negativas

* dependência do ambiente Docker

---
