# **FutureStack – Health Score API**

O **FutureStack – Health Score API** é uma plataforma desenvolvida para monitorar o bem-estar profissional dos usuários através de check-ins diários, cálculo de score (0–1000) e geração de recomendações com **IA Generativa usando Spring AI**. A solução combina mensageria assíncrona, caching, internacionalização e segurança completa com JWT.

---

## 📌 **Tecnologias Utilizadas**

- **Java 21**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **Bean Validation**
- **Spring Cache**
- **Internacionalização** (pt-BR e en-US)
- **RabbitMQ** (Mensageria)
- **Spring AI** (Groq)
- **Maven**

---

## 🧠 **Funcionalidades Principais**

- 🔐 **Autenticação & Autorização** com JWT
- 📊 **Check-ins Diários** para monitoramento do bem-estar
- 🎯 **Cálculo Automático de Score** (0-1000 pontos)
- 🤖 **Recomendações Personalizadas** via IA Generativa
- 💬 **Chat de Suporte Emocional** com IA
- ⚡ **Processamento Assíncrono** com RabbitMQ
- 🚀 **Cache** para otimização de performance
- 🌍 **Internacionalização** (pt-BR e en-US)
- 📈 **Relatórios e Métricas** (média mensal, resumos)

---

## 📚 **Documentação**

### 🔐 **Autenticação**

#### **Cadastrar Usuário**
**POST** `/api/users`

**Request:**
```json
{
  "nome": "Amanda Nunes",
  "email": "amanda@example.com",
  "password": "123456"
}
```

#### **Login**
**POST** `/api/auth/login`

**Request:**
```json
{
  "email": "amanda@example.com",
  "password": "123456"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6...",
  "type": "Bearer"
}
```

**Use o token em todas as requisições:**
```
Authorization: Bearer SEU_TOKEN_JWT
```

---

### 👤 **Gerenciamento de Usuário**

#### **Atualizar Usuário**
**PUT** `/api/users`

**Request:**
```json
{
  "nome": "Amanda Nunes",
  "email": "amanda.nova@example.com"
}
```

#### **Obter Meus Dados**
**GET** `/api/users/me`

---

### 📊 **Check-ins e Monitoramento**

#### **Criar Check-in**
**POST** `/api/checkins`

**Request:**
```json
{
  "mood": 6,
  "energy": 7,
  "sleep": 6,
  "focus": 8,
  "hoursWorked": 5
}
```

**Response:**
```json
{
  "id": 42,
  "date": "2025-11-16T19:22:10.402",
  "score": 720,
  "mood": 6,
  "energy": 7,
  "sleep": 6,
  "focus": 8,
  "hoursWorked": 5
}
```

#### **Último Check-in**
**GET** `/api/checkins/last`

#### **Média Mensal**
**GET** `/api/checkins/monthly-avg`

---

### 🤖 **IA Generativa & Recomendações**

#### **Recomendação Diária**
**GET** `/api/ai/daily`

**Response:**
```json
{
  "recommendation": "Hoje seu nível de sono está abaixo da média. Priorize um descanso mais profundo esta noite..."
}
```

#### **Resumo Semanal/Mensal**
**GET** `/api/ai/monthly-summary`

#### **Chat com IA**
**POST** `/api/ai/chat`

**Request:**
```json
{
  "message": "Estou muito cansada hoje, como posso equilibrar minha energia?"
}
```

**Response:**
```json
{
  "response": "Percebo que seus níveis recentes de energia estão baixos. Tente programar pequenas pausas ao longo do dia..."
}
```

---

## 🌍 **Internacionalização (i18n)**

A API suporta **português (pt-BR)** e **inglês (en-US)**.

**Exemplo de uso:**
```bash
# Português (padrão)
GET /api/checkins/last

# Inglês
GET /api/checkins/last?lang=en_US
```

---

## ⚡ **Arquitetura & Mensageria**

### **Fluxo de Check-in com RabbitMQ**
1. ✅ Usuário envia check-in
2. 📨 Evento é publicado na fila `CHECKIN_QUEUE`
3. 🤖 Consumer processa e gera recomendação via IA
4. 💾 Recomendação é salva no banco
5. 🔔 Usuário recebe recomendação personalizada

**Estrutura do Evento:**
```json
{
  "userId": 1,
  "checkInId": 42,
  "score": 720,
  "timestamp": "2025-11-16T19:22:10.402"
}
```

---

## 🐳 **Deploy:**
- **Link para acesso:**: [Download Link](#)
---

## 📱 **Integração Mobile**

Este backend é consumido pelo aplicativo mobile em React Native:

- **Repositório Frontend**: [github.com/seu-usuario/futurestack-mobile](https://github.com/seu-usuario/futurestack-mobile)
- **APK**: [Download Link](#)

---

## 🎬 **Vídeos e Demonstrações**

🎯 **Vídeo Pitch**: [Link para o vídeo pitch](#)  
📱 **Vídeo Demonstração**: [Link para demonstração](#)  

---

## 👥 **Equipe de Desenvolvimento**

| Integrante | RM |
|------------|-----|
| **Mariana Christina** | RM554773 |
| **Gabriela Moguinho** |RM556143 |
| **Henrique Maciel** | RM556480 |
