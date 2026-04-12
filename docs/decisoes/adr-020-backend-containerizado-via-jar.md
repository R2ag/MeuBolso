# 📄 ADR-020 — Backend containerizado via JAR

---

## Status

Aceito

---

## Contexto

O backend precisa ser empacotado de forma simples e eficiente.

---

## Decisão

Gerar:

```text
JAR executável (Spring Boot)
```

E rodar dentro do container.

---

## Consequências

### Positivas

* simplicidade
* compatibilidade com Docker
* fácil deploy

---

### Negativas

* build necessário antes da execução

---
