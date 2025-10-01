# BiblioTech API: Sistema de Gerenciamento de Biblioteca Online

## 📖 Sobre o Projeto

A **BiblioTech API** é uma API RESTful projetada para que usuários e administradores possam gerenciar informações sobre o acervo de uma biblioteca digital. O objetivo é fornecer uma plataforma onde os administradores possam registrar, consultar, atualizar e remover livros, e os usuários possam consultar o catálogo, realizar empréstimos e acompanhar seu histórico.

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

  * Na raiz do projeto, execute o comando abaixo no seu terminal para iniciar um contêiner PostgreSQL.
    ```sh
    docker run --name bibliotech-postgres -e POSTGRES_PASSWORD=minhasenha -e POSTGRES_DB=bibliotech_db -p 5432:5432 -d postgres:17
    ```

**3. Configure as Variáveis de Ambiente no VSCode:**

  * Por segurança, senhas e segredos são lidos de variáveis de ambiente.
  * Na raiz do projeto, crie uma pasta `.vscode` e, dentro dela, um arquivo chamado `launch.json`.
  * Cole o seguinte conteúdo no `launch.json`.
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
  * **Importante:** Adicione a linha `.vscode/` ao seu arquivo `.gitignore` para que este arquivo com segredos não seja enviado para o repositório.

**4. Execute a Aplicação:**

  * Abra a aba "Run and Debug" (Executar e Depurar) no VSCode.
  * Selecione a configuração "BiblioTechApiApplication" e clique no ícone de play (▶️).
  * O servidor será iniciado em `http://localhost:8080`.

## 📚 Documentação e Teste da API

A forma mais fácil de testar é através da documentação interativa do Swagger.

  * **Acesse a Documentação:** `http://localhost:8080/swagger-ui.html`

### **Guia Rápido de Teste Manual**

Para testar as permissões, você precisará de um usuário `ADMIN` e um `USER`.

**1. Crie um Usuário `ADMIN`:**

  * Modifique temporariamente o `AuthenticationService.java`, na linha `newUser.setRole(Role.USER);` para `newUser.setRole(Role.ADMIN);`.
  * Inicie a API.
  * Use o endpoint `POST /auth/register` (pode ser pelo Swagger) para criar um usuário administrador. Ex:
    ```json
    {
      "name": "Admin",
      "email": "admin@email.com",
      "password": "admin123"
    }
    ```

**2. Crie um Usuário `USER`:**

  * Volte a linha no `AuthenticationService` para `newUser.setRole(Role.USER);`.
  * Reinicie a API.
  * Use o `POST /auth/register` para criar um usuário comum. Ex:
    ```json
    {
      "name": "Leitor",
      "email": "leitor@email.com",
      "password": "leitor123"
    }
    ```

**3. Teste os Endpoints:**

  * Use o `POST /auth/login` para obter os tokens JWT de cada usuário.
  * No Swagger, clique no botão **"Authorize"** no topo da página e cole o token JWT no formato `Bearer <seu_token>` para autenticar suas requisições.
  * **Teste como ADMIN:** Tente criar um autor ou livro (`POST /authors`, `POST /books`). A operação deve funcionar.
  * **Teste como USER:**
      * Tente criar um autor. A operação deve falhar com erro **403 Forbidden**.
      * Tente pegar um livro emprestado (`POST /loans`). A operação deve funcionar.
      * Tente ver todos os empréstimos (`GET /loans`). A operação deve falhar com erro **403 Forbidden**.
      * Tente ver seus próprios empréstimos (`GET /loans/me`). A operação deve funcionar.