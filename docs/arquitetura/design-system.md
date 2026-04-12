# 🎯 Design System — MeuBolso

---

## 📌 Objetivo

Definir padrões visuais, comportamentais e estruturais do frontend, garantindo:

* consistência de UI
* escalabilidade
* fácil manutenção
* suporte a temas (light/dark)
* reutilização via design tokens

---

## 🧱 Base do sistema

---

O design system será baseado em:

```text
PrimeNG + Tema customizado + Design Tokens + Suporte a Dark Mode
```

---

# 🎯 1. Princípios de Design

---

* Clareza > Estética
* Informação > Decoração
* Consistência > Criatividade
* Feedback imediato ao usuário
* Acessibilidade como padrão

---

# 🎨 2. Design Tokens

---

## 📌 Conceito

Design tokens são variáveis centralizadas que definem:

* cores
* espaçamentos
* tipografia
* bordas
* sombras

---

## 📦 Estrutura

```text
tokens/
├── colors.css
├── spacing.css
├── typography.css
├── radius.css
└── shadows.css
```

---

## 🎨 2.1 Tokens de cor

---

### 🌞 Light Mode

```css
:root {
  --color-primary: #2563EB;

  --color-success: #16A34A;
  --color-danger: #DC2626;
  --color-warning: #F59E0B;

  --color-bg: #F9FAFB;
  --color-surface: #FFFFFF;

  --color-text: #111827;
  --color-text-secondary: #6B7280;

  --color-border: #E5E7EB;
}
```

---

### 🌙 Dark Mode

```css
[data-theme="dark"] {
  --color-primary: #3B82F6;

  --color-success: #22C55E;
  --color-danger: #EF4444;
  --color-warning: #FBBF24;

  --color-bg: #111827;
  --color-surface: #1F2933;

  --color-text: #F9FAFB;
  --color-text-secondary: #9CA3AF;

  --color-border: #374151;
}
```

---

## 📏 2.2 Espaçamento

```css
:root {
  --spacing-1: 4px;
  --spacing-2: 8px;
  --spacing-3: 12px;
  --spacing-4: 16px;
  --spacing-5: 24px;
  --spacing-6: 32px;
}
```

---

## 🔤 2.3 Tipografia

```css
:root {
  --font-family: 'Inter', 'Roboto', sans-serif;

  --font-size-sm: 12px;
  --font-size-md: 14px;
  --font-size-lg: 18px;
  --font-size-xl: 24px;

  --font-weight-regular: 400;
  --font-weight-bold: 600;
}
```

---

## 🔲 2.4 Bordas

```css
:root {
  --radius-sm: 4px;
  --radius-md: 8px;
  --radius-lg: 12px;
}
```

---

## 🌫️ 2.5 Sombras

```css
:root {
  --shadow-sm: 0 1px 2px rgba(0,0,0,0.05);
  --shadow-md: 0 4px 6px rgba(0,0,0,0.1);
}
```

---

# 🌗 3. Dark Mode

---

## 📌 Estratégia

```text
Uso de atributo: data-theme="dark"
```

---

## 📌 Alternância

* controle via usuário
* persistência em localStorage
* fallback automático (prefers-color-scheme)

---

## 📌 Regras

* nunca usar cores fixas (hex direto)
* sempre usar design tokens
* garantir contraste adequado

---

# 🎨 4. Identidade visual

---

## 🎯 Cores semânticas

| Tipo    | Uso              |
| ------- | ---------------- |
| Primary | ações principais |
| Success | receitas         |
| Danger  | despesas/erros   |
| Warning | atrasos          |

---

---

# 🧩 5. Componentes (PrimeNG)

---

## 🔘 Botões (`p-button`)

---

### Tipos

* Primary
* Secondary
* Danger
* Text

---

### Regras

* 1 ação primária por tela
* estados:

  * loading
  * disabled

---

---

## 📊 Tabelas (`p-table`)

---

### Padrões obrigatórios

* paginação
* ordenação
* filtros
* responsividade

---

### Financeiro

* valores coloridos por tipo
* alinhamento à direita

---

---

## 🧾 Inputs

---

* label sempre visível
* validação clara
* feedback imediato

---

---

## 🪟 Modais (`p-dialog`)

---

* foco em ações críticas
* evitar excesso de conteúdo

---

---

## 🧭 Navegação

---

### Estrutura

* Sidebar
* Conteúdo principal

---

### Itens

* Dashboard
* Lançamentos
* Contas a pagar
* Contas a receber

---

---

# 💰 6. Padrões financeiros

---

## 📊 Cores

```text
Receita → Success
Despesa → Danger
Neutro → Texto padrão
```

---

## 📊 Formato

```text
R$ 1.234,56
```

---

---

# ⚠️ 7. Feedback ao usuário

---

### Tipos

* Success (toast)
* Error (toast)
* Warning (toast)
* Info (toast)

---

---

# 🔄 8. Estados de interface

---

### Loading

* skeleton
* spinner

---

### Vazio

* mensagem clara
* ação sugerida

---

---

# 📱 9. Responsividade

---

## Breakpoints

```text
Mobile < 768px
Tablet < 1024px
Desktop ≥ 1024px
```

---

## Regras

* tabelas → scroll horizontal
* sidebar → colapsável

---

---

#   10. Organização do frontend

---

```text
src/app/
├── core/
├── shared/
│   ├── components/
│   ├── ui/ (wrappers do PrimeNG)
│   └── tokens/
│
├── features/
│   ├── financeiro/
│   ├── obrigacoes/
│   ├── recebiveis/
│   └── dashboard/
```

---

---

# 🧠 11. Boas práticas

---

* nunca usar estilos inline
* usar tokens sempre
* evitar duplicação de estilos
* criar wrappers para componentes complexos

---