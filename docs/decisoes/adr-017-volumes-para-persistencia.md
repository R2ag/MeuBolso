# 📄 ADR-017 — Uso de volumes para persistência de dados

---

## Status

Aceito

---

## Contexto

Os dados do banco não podem ser perdidos ao reiniciar containers.

---

## Decisão

Utilizar:

```text
Volumes Docker para persistência
```

---

## Consequências

### Positivas

* persistência garantida
* independência do ciclo de vida do container

---

### Negativas

* necessidade de gerenciar volumes

---
