# 🛒 Order Management API

## 📖 Sobre o Projeto

A **Blacksmith's Online Store API** é uma aplicação desenvolvida em **Java com Spring Boot** que tem como objetivo gerenciar o fluxo de pedidos, produtos e usuários em um sistema de e-commerce com tema medieval.

O projeto foi construído com foco em **boas práticas de arquitetura**, **segurança com JWT**, e **organização de código**, seguindo padrões utilizados em empresas para vagas **pleno backend Java/Spring**.

Este projeto também foi idealizado como parte de um **desafio técnico pessoal**, simulando um ambiente real de desenvolvimento profissional.

---

## 🧩 Funcionalidades Principais

### 👤 Autenticação e Autorização
- Implementação de **Spring Security** com **JWT (Auth0 Java JWT)**.
- Controle de acesso baseado em **roles** (`ADMIN` e `CUSTOMER`).
- Apenas `ADMIN` pode gerenciar produtos, ferreiros e visualizar todos os pedidos.
- Usuários `CUSTOMER` podem criar e visualizar apenas seus próprios pedidos.
- Criptografia de senhas com **BCryptPasswordEncoder**.

### 🧍 Usuários (`User`)
- Cadastro e autenticação de usuários.
- Campos: `id`, `username`, `password`, `role`, `birthDate`.
- Validação de idade mínima (18 anos).
- Conversão entre **entidade** e **DTOs** para manter o encapsulamento dos dados.

### 📦 Armas (`Weapon`)
- Cadastro, atualização e exclusão de produtos (somente `ADMIN`).
- Campos: `id`, `name`, `type`, `rarity`, `material`, `baseDamage`, `weight`, `description`, `price`, `stockQuantity`, `craftedBy`.
- Regras de negócio simples de controle de estoque.

### 🧾 Pedidos (`Order`)
- Associação de pedidos ao usuário autenticado.
- Cálculo automático do valor total do pedido.
- Apenas o cliente pode acessar seus próprios pedidos.
- Admins têm acesso global para fins de auditoria.

---

## ⚙️ Tecnologias Utilizadas

| Categoria | Tecnologias |
|------------|--------------|
| **Linguagem** | Java 17 |
| **Framework principal** | Spring Boot 3 |
| **Segurança** | Spring Security + JWT (Auth0) |
| **Persistência** | Spring Data JPA + Hibernate |
| **Banco de Dados** | PostgreSQL |
| **Build & Dependências** | Maven |
| **Validações** | Jakarta Bean Validation (javax/jakarta.validation) |
| **Documentação** | Swagger / Springdoc OpenAPI |
| **Utilitários** | Lombok, MapStruct (opcional) |

---

## 🧱 Arquitetura do Projeto

A arquitetura segue o modelo de **camadas** (layered architecture), com separação clara de responsabilidades:
com.anthony.blacksmithOnlineStore <br>
│ <br>
├── controller → Camada de entrada da aplicação (endpoints REST) <br>
├── service → Contém a lógica de negócio <br>
├── repository → Interface com o banco de dados (Spring Data JPA)<br>
├── security → Configuração de segurança e JWT<br>
├── dto → Objetos de transferência de dados (entrada e saída)<br>
├── entity → Mapeamento JPA das entidades<br>
├── enums → Enumerações (ex: Role)<br>
└── exception → Exceções personalizadas e handlers globais<br>


Essa estrutura garante:
- Coesão interna em cada camada
- Baixo acoplamento entre componentes
- Facilidade para testes e manutenção

---

## 🔐 Segurança

A autenticação é baseada em **JWT (JSON Web Token)**.  
Após o login bem-sucedido, o usuário recebe um token que deve ser enviado no cabeçalho `Authorization` de cada requisição:
`Authorization: Bearer <seu_token_aqui>`


A autorização é controlada através de anotações como:

```java
@PreAuthorize("hasRole('ADMIN')")
@PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
```
---

## 🚀 Como Executar o Projeto
Pré-requisitos

- Java 17+
- Maven 3.8+
- PostgreSQL em execução

### 1️⃣ Clone o repositório
git clone https://github.com/seuusuario/order-management-api.git
cd order-management-api

### 2️⃣ Configure o banco de dados (Opcional)
#### Edite o arquivo src/main/resources/application.properties:
spring.datasource.url=jdbc:postgresql://localhost:5432/order_management
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update

### 3️⃣ Compile e execute
mvn spring-boot:run <br>
Ou diretamente em sua IDE favorita.

---

## 🧠 Decisões Técnicas

- Utilização de DTOs para isolamento entre a API e a camada de persistência.

- Métodos fromEntity() e toEntity() para conversões claras e centralizadas.

- Enum Role implementando GrantedAuthority, garantindo integração limpa com o Spring Security.

- Tratamento de exceções personalizado, retornando respostas claras e padronizadas para o cliente.

- Optional + Exceptions customizadas no serviço para evitar null e if aninhados.

- Validações com Bean Validation (ex: idade mínima para cadastro).

---

## 📘 Exemplos de Endpoints
### Autenticação
`POST /auth/register`

`POST /auth/login`

### Armas
`GET /weapons`

`POST /weapons`        # ADMIN

`PUT /weapons/{id}`    # ADMIN

`DELETE /weapons/{id}` # ADMIN

### Pedidos
`POST /orders`          # CUSTOMER

`GET /orders`           # CUSTOMER (somente seus pedidos)

`GET /orders/all`       # ADMIN

### Avaliação
- `POST /api/avaliacoes` → Avaliar arma
- `GET /api/armas/{id}/avaliacoes` → Listar avaliações de uma arma

