# ADR-001: Uso de Monorepo

## 📌 Contexto

O sistema possui frontend e backend fortemente acoplados
em termos de domínio e evolução.

---

## ✅ Decisão

Adotar monorepo com separação por contexto.

---

## 🎯 Consequências

### Positivas
- Versionamento único
- Compartilhamento de contratos
- Facilidade de refatoração

### Negativas
- Build mais complexo
- Necessidade de organização rigorosa