Perfeito — agora vamos formalizar todas as decisões de infraestrutura em **ADRs**, mantendo o mesmo padrão consistente que você já adotou.

---

# 📂 `docs/adr/` — Infraestrutura

---

# 📄 ADR-013 — Uso de Docker para containerização

---

## Status

Aceito

---

## Contexto

O projeto precisa de um ambiente padronizado, reprodutível e isolado para desenvolvimento e futura implantação.

---

## Decisão

Adotar o uso de:

```text
Docker para containerização dos serviços
```

---

## Consequências

### Positivas

* ambiente consistente entre desenvolvedores
* isolamento de dependências
* facilidade de setup
* base para deploy em cloud

---

### Negativas

* curva de aprendizado inicial
* necessidade de gerenciar imagens

---