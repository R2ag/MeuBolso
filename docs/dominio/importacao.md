# 🔄 Importação de Transações

## 🎯 Objetivo

Permitir importar transações de:

- CSV
- OFX
- APIs (futuro)

---

## 🧠 Pipeline

1. Upload
2. Parsing
3. Normalização
4. Deduplicação
5. Classificação
6. Revisão
7. Conversão em lançamento

---

## ⚠️ Regras

- Nunca criar lançamento direto
- Sempre passar por staging
- Evitar duplicidade

---

## 📦 Entidades

### Importacao
Representa um lote de importação

### TransacaoImportada
Representa uma transação ainda não processada