# 📄 ADR-028 — Adoção de stack moderna (latest-first)

---

## Status

Aceito

---

## Contexto

Foi decidido utilizar versões recentes das tecnologias principais.

---

## Decisão

Adotar estratégia:

```text
Preferência por versões mais recentes estáveis
```

---

## Consequências

### Positivas

* acesso a novos recursos
* maior longevidade do sistema
* menor dívida técnica futura

---

### Negativas

* maior risco de bugs iniciais
* menor maturidade de documentação

---

# 🔄 Atualização implícita dos ADRs anteriores

---

Esses ADRs atualizam diretamente:

* ADR-004 (Banco de dados) → agora especifica versão 18
* ADR-002 (Arquitetura backend) → compatível com Spring 4
* ADR-013/014 (Docker) → imagens devem refletir versões novas

---
