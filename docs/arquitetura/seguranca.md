# 🔐 Segurança do Sistema — MeuBolso

---

## 🎯 Objetivo

Garantir a proteção de:

* dados financeiros do usuário
* autenticação e identidade
* integridade das operações
* acesso controlado aos recursos

---

# 🧠 1. Princípios de segurança

---

* Segurança por padrão
* Menor privilégio possível
* Defesa em profundidade
* Dados sensíveis sempre protegidos
* Falhas devem ser seguras (fail-safe)

---

# 👤 2. Modelo de autenticação

---

## 📌 Estratégia

```text
Autenticação própria (email + senha)
```

---

## 📌 Fluxo

```text
1. Usuário envia email + senha
2. Backend valida credenciais
3. Gera token JWT
4. Cliente envia token em cada requisição
```

---

## 📌 Token

```text
JWT (JSON Web Token)
```

---

### Conteúdo do token

```json
{
  "sub": "userId",
  "email": "user@email.com",
  "iat": "...",
  "exp": "..."
}
```

---

## 📌 Armazenamento no frontend

* Preferencial: **HttpOnly Cookie**
* Alternativa: memória (evitar localStorage)

---

# 🔑 3. Gestão de usuários

---

## 📌 Cadastro

* email único
* senha obrigatória

---

## 📌 Senha

### Regras

* mínimo 8 caracteres
* conter letras e números

---

### Armazenamento

```text
Hash seguro (bcrypt)
```

---

## 📌 Nunca armazenar

* senha em texto puro
* tokens sem criptografia

---

#  4. Autorização

---

## 📌 Estratégia inicial

```text
Controle por usuário (multi-tenant simples)
```

---

## 📌 Regra fundamental

```text
Usuário só acessa seus próprios dados
```

---

## 📌 Implementação

* todas as entidades devem ter `userId`
* filtros obrigatórios por usuário

---

#  5. Proteção de endpoints

---

## 📌 Regras

* endpoints autenticados por padrão
* exceções:

  * login
  * cadastro

---

## 📌 Headers obrigatórios

```text
Authorization: Bearer <token>
```

---

---

#  6. Proteção de dados

---

## 📌 Em trânsito

```text
HTTPS obrigatório
```

---

## 📌 Em repouso

* banco protegido por credenciais
* backups seguros

---

## 📌 Dados sensíveis

* evitar logs com dados financeiros
* mascarar informações quando necessário

---

#  7. Validação de dados

---

## 📌 Backend é a fonte da verdade

* nunca confiar no frontend
* validar tudo no backend

---

## 📌 Exemplos

* valores financeiros
* datas
* IDs

---

#  8. Proteção contra ataques comuns

---

## 🔹 SQL Injection

* uso de ORM (JPA)
* queries parametrizadas

---

## 🔹 XSS (Cross-Site Scripting)

* Angular já protege por padrão
* evitar innerHTML

---

## 🔹 CSRF

* usar cookie HttpOnly + SameSite
* ou desabilitar se usar JWT stateless corretamente

---

## 🔹 Brute Force

* limitar tentativas de login
* bloqueio temporário

---

## 🔹 Enumeração de usuário

* mensagens genéricas:

```text
"Credenciais inválidas"
```

---

#  9. Expiração e renovação de sessão

---

## 📌 Token

```text
Expiração curta (ex: 15–60 min)
```

---

## 📌 Refresh Token

(opcional futuro)

* renovação sem novo login
* armazenado com mais segurança

---

#  10. Auditoria (futuro)

---

Registrar eventos:

```text
Login
Logout
Falha de login
Criação de dados
Alterações críticas
```

---

#  11. Logs

---

## 📌 Regras

* não logar senhas
* não logar tokens completos
* mascarar dados sensíveis

---

---

#  12. Integração com domínio

---

## 📌 Regra fundamental

```text
Toda entidade pertence a um usuário
```

---

## 📌 Exemplo

```text
ContaPagar → userId
ContaReceber → userId
Lancamento → userId
```

---

---

#  13. Configuração no Spring

---

## 📌 Componentes

* Spring Security
* JWT Filter
* AuthenticationProvider

---

---

## 📌 Estrutura

```text
security/
├── config/
├── jwt/
├── filter/
└── service/
```

---


# 14. Regras críticas

---

```text
- Nunca confiar no frontend
- Nunca expor dados de outro usuário
- Nunca armazenar senha sem hash
- Sempre validar autorização
```

---

