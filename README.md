# BiblioTech API: Sistema de Gerenciamento de Biblioteca Online

## 📖 Sobre o Projeto

A **BiblioTech API** é uma API RESTful projetada para que usuários e administradores possam gerenciar informações sobre o acervo de uma biblioteca digital.
O objetivo é fornecer uma plataforma onde os administradores possam registrar, consultar, atualizar e remover livros, e os usuários possam consultar o catálogo, realizar empréstimos e acompanhar seu histórico.

O projeto foi construído seguindo as melhores práticas de desenvolvimento, incluindo uma arquitetura em camadas (Controller, Service, Repository), o uso do padrão DTO para transferência de dados e um sistema de autenticação seguro baseado em tokens JWT.

## ✨ Funcionalidades Principais

* **Autenticação Segura com JWT:** Sistema de registro e login utilizando JSON Web Tokens para uma autenticação *stateless*.
* **Controle de Acesso Baseado em Papéis (Roles):** Dois níveis de permissão para proteger os endpoints.

  * `ADMIN`: Acesso total ao gerenciamento do acervo.
  * `USER`: Acesso para consulta e realização de empréstimos.
* **Gerenciamento Completo do Acervo:** Operações de CRUD (Criar, Ler, Atualizar, Deletar) para Livros (`Book`) e Autores (`Author`).
* **Sistema de Empréstimos e Devoluções:** Lógica completa para solicitar (`POST /loans`), devolver (`PATCH /loans/{id}/return`) e consultar empréstimos (`Loan`), com controle de status (`ACTIVE`, `RETURNED`, `OVERDUE`).
* **Tratamento de Exceções Centralizado:** Uso de `@ControllerAdvice` para fornecer respostas de erro JSON padronizadas e claras.
* **Documentação Interativa com Swagger:** Geração automática da documentação da API com OpenAPI 3 para fácil exploração e teste.

## 🛠️ Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 3**
* **Spring Web**
* **Spring Security** (com autenticação JWT)
* **Spring Data JPA** (com Hibernate)
* **PostgreSQL** (executando em Docker)
* **Maven**
* **Auth0 Java JWT** (para manipulação de tokens)
* **Springdoc OpenAPI** (para documentação Swagger)

## 🚀 Como Rodar o Projeto

Siga os passos abaixo para configurar e executar o ambiente de desenvolvimento local.

### **Pré-requisitos**

* **Java Development Kit (JDK) 21** ou superior.
* **Apache Maven 3.8** ou superior.
* **Docker Desktop**.
* **Visual Studio Code** com o "Extension Pack for Java".

### **Passo a Passo**

**1. Clone o Repositório:**

```sh
git clone https://github.com/mathzsl/bibliotech_api
cd BiblioTech-API
```

**2. Inicie o Banco de Dados com Docker:**

```sh
docker run --name bibliotech-postgres -e POSTGRES_PASSWORD=minhasenha -e POSTGRES_DB=bibliotech_db -p 5432:5432 -d postgres:17
```

**3. Configure as Variáveis de Ambiente no VSCode:**

* Por segurança, senhas e segredos são lidos de variáveis de ambiente.
* Na raiz do projeto, crie uma pasta `.vscode` e, dentro dela, um arquivo chamado `launch.json`.
* Cole o seguinte conteúdo:

```json
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "BiblioTechApiApplication",
      "request": "launch",
      "mainClass": "br.com.bibliotech.bibliotechapi.BiblioTechApiApplication",
      "projectName": "BiblioTech-API",
      "env": {
        "DB_PASSWORD": "minhasenha",
        "JWT_SECRET": "bQeThWmZq4t7w!z$C&F)J@NcRfUjXn2r5u8x/A?D(G+KbPeSgVkYp3s6v9y$B&E)"
      }
    }
  ]
}
```

⚠️ **Importante:** adicione `.vscode/` ao `.gitignore` para não versionar esse arquivo com segredos.

**4. Execute a Aplicação:**

* Abra a aba **Run and Debug** no VSCode.
* Selecione a configuração **BiblioTechApiApplication** e clique em ▶️.
* O servidor será iniciado em `http://localhost:8080`.

## 📚 Documentação da API

A forma mais fácil de explorar a API é através da documentação interativa do Swagger.

* **URL:** `http://localhost:8080/swagger-ui.html`

## 🧪 Fluxo de Teste (Exemplo Prático)

Este guia mostra um cenário completo: criar um usuário, logar, criar acervo como admin e pegar um livro emprestado como usuário.

### **1. Crie os Usuários**

* **ADMIN:**

  * Alterar temporariamente `AuthenticationService.java` para `newUser.setRole(Role.ADMIN);`
  * Requisição: `POST /auth/register`
  * Body:

    ```json
    { "name": "Admin", "email": "admin@email.com", "password": "admin123" }
    ```

* **USER:**

  * Voltar `AuthenticationService` para `Role.USER`.
  * Requisição: `POST /auth/register`
  * Body:

    ```json
    { "name": "Leitor", "email": "leitor@email.com", "password": "leitor123" }
    ```

### **2. Autentique-se e Crie o Acervo**

* **Login ADMIN:**

  * `POST /auth/login`
  * Body:

    ```json
    { "email": "admin@email.com", "password": "admin123" }
    ```
  * Resposta esperada:

    ```json
    {
      "accessToken": "ey...",
      "user": { "email": "admin@email.com", "name": "Admin" }
    }
    ```
  * Copie o `accessToken` → `TOKEN_ADMIN`.

* **Criar Autor:**

  * `POST /authors` (Bearer `TOKEN_ADMIN`)
  * Body:

    ```json
    { "name": "Frank Herbert", "nationality": "American" }
    ```
  * Copie o `id` do autor.

* **Criar Livro:**

  * `POST /books` (Bearer `TOKEN_ADMIN`)
  * Body:

    ```json
    {
      "title": "Dune",
      "category": "Science Fiction",
      "isbn": "978-0441013593",
      "publicationYear": 1965,
      "availableCopies": 1,
      "authorId": "COLE_O_ID_DO_AUTOR_AQUI"
    }
    ```
  * Copie o `id` do livro.

### **3. Realize um Empréstimo**

* **Login USER:**

  * `POST /auth/login`
  * Body:

    ```json
    { "email": "leitor@email.com", "password": "leitor123" }
    ```
  * Copie o `accessToken` → `TOKEN_USER`.

* **Pegar Livro Emprestado:**

  * `POST /loans` (Bearer `TOKEN_USER`)
  * Body:

    ```json
    { "bookId": "COLE_O_ID_DO_LIVRO_AQUI" }
    ```
  * Resposta esperada:

    ```json
    {
      "id": "c1f2b6e4-...",
      "loanDate": "2025-10-01",
      "dueDate": "2025-10-15",
      "returnDate": null,
      "status": "ACTIVE",
      "user": { /* ... */ },
      "book": { /* ... */ }
    }
    ```

* **Consultar Empréstimos:**

  * `GET /loans/me` (Bearer `TOKEN_USER`)
  * Resposta:

    ```json
    [
      {
        "id": "c1f2b6e4-...",
        "loanDate": "2025-10-01",
        "dueDate": "2025-10-15",
        "returnDate": null,
        "status": "ACTIVE",
        "book": {
          "title": "Dune",
          "author": { "name": "Frank Herbert" }
        }
      }
    ]
    ```

---
