# 📄 ADR-005 — Autenticação via JWT (sem OAuth)

---

## Status

Aceito

---

## Contexto

Precisamos de autenticação simples, sem dependência de terceiros.

---

## Decisão

Utilizar:

```text
Autenticação própria (email + senha) + JWT
```

Sem integração com OAuth (Google, etc).

---

## Consequências

### Positivas

* controle total
* independência de terceiros
* simplicidade

---

### Negativas

* responsabilidade total sobre segurança
* necessidade de implementar fluxo completo

---
