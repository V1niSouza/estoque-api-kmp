# 📦 Stock API - Kotlin Multiplatform + Ktor + Supabase

## 📌 Descrição do Projeto

API REST desenvolvida com Kotlin Multiplatform e Ktor para gerenciamento de estoque de produtos, conectada ao Supabase (PostgreSQL).

---

## 🧱 Tecnologias
- Kotlin Multiplatform
- Ktor Server
- Supabase (PostgreSQL)
- kotlinx.serialization

---

## 🗄️ Banco de Dados

### products
- id (UUID)
- name
- description
- sku (unique)
- category
- created_at
- updated_at

### stock_items
- id (UUID)
- product_id (FK)
- quantity
- unit_price
- location
- updated_at

ON DELETE CASCADE em products → stock_items

---

## 🌐 Rotas

### PRODUCTS
GET /products  
GET /products/{id}  
POST /products  
PUT /products/{id}  
DELETE /products/{id}

### STOCK
GET /stock  
GET /stock/{id}  
POST /stock  
PUT /stock/{id}  
DELETE /stock/{id}

### SUMMARY
GET /stock/summary

---

## 📥 JSON EXEMPLOS

### Criar produto
{
  "name": "Caneta Azul",
  "description": "Esferográfica",
  "sku": "CAN-001",
  "category": "Escritório"
}

### Criar estoque
{
  "product_id": "uuid",
  "quantity": 10,
  "unit_price": 12.90,
  "location": "A1"
}

### Update estoque
{
  "quantity": 20,
  "unit_price": 15.00
}

---

## 📊 Summary retorno
[
  {
    "product_id": "uuid",
    "product_name": "Caneta Azul",
    "total_quantity": 100
  }
]

---
## 🚀 Execução do Projeto

### 📥 1. Clonar o repositório

```bash
git clone <URL-DO-REPOSITORIO>
cd estoque-api-kmp
```

---

### 🗄️ 2. Configurar o banco de dados (Supabase)

O projeto já contém um arquivo `.sql` na raiz do repositório.

👉 Para configurar o banco:

1. Abra o Supabase
2. Vá em **SQL Editor**
3. Cole e execute o arquivo `.sql`

Isso irá criar automaticamente:

- Tabela `products`
- Tabela `stock_items`
- Relacionamento entre tabelas (Foreign Key com `ON DELETE CASCADE`)

---

### 🔐 3. Configurar variáveis de ambiente

Na raiz do projeto, crie o arquivo:

```bash
local.properties
```

Conteúdo:

```properties
SUPABASE_URL=sua-url-aqui
SUPABASE_KEY=sua-anon-key-aqui
```

📌 As credenciais podem ser obtidas no painel do Supabase.

---

### ⚙️ 4. Configurar Gradle (Java/JDK)

Edite o arquivo `gradle.properties` e ajuste o caminho do Java:

#### 🪟 Windows
```properties
org.gradle.java.home=C:\\Program Files\\Android\\Android Studio\\jbr
```

#### 🐧 Linux
```properties
org.gradle.java.home=/usr/lib/jvm/java-21-openjdk-amd64
```

---

### ▶️ 5. Executar o projeto

```bash
./gradlew :server:run
```

---

## 👨‍💻 Autor

**Vinícius Souza Ramos**