# 🧩 Desafio Fullstack Integrado — EJB + Spring Boot + Angular

## 📖 Descrição Geral

O **Desafio Fullstack Integrado** simula uma aplicação corporativa completa, composta por múltiplos módulos **Java 17**, **Spring Boot**, **EJB/WildFly**, **Angular 19** e **PostgreSQL**.  
O objetivo é demonstrar **integração entre sistemas distribuídos**, com comunicação entre um **serviço EJB** e uma **API REST Spring Boot**, além de um **frontend funcional** para gerenciamento de benefícios e transferência de saldo.

---

## 🧱 Estrutura do Projeto

```text
bip-teste-integrado/
│
├── .github/
│   └── workflows/
│       └── ci.yml                  # Pipeline CI (GitHub Actions)
│
├── backend-module/                 # API Spring Boot
│   ├── src/main/java/com/example/backend/
│   │   ├── application/            # Camada de aplicação (DTOs, Exceptions, Services)
│   │   ├── config/                 # Configurações gerais (CORS, OpenAPI, etc)
│   │   ├── controller/             # Controladores REST
│   │   ├── domain/                 # Domínio (Modelos, Regras, Serviços de Negócio)
│   │   ├── infrastructure/         # Infraestrutura (Repositories, Mappers, Adapters)
│   │   └── integration/            # Integração com EJB remoto
│   ├── resources/
│   │   ├── db/migration/           # Scripts Flyway (migrations)
│   │   ├── templates/              # Templates (se aplicável)
│   │   └── application.yml         # Configurações do Spring Boot
│   └── pom.xml
│
├── ejb-module/                     # Serviço EJB (WildFly)
│   ├── src/main/java/com/example/ejb/
│   │   ├── application/            # DTOs, Exceptions, Mappers
│   │   ├── domain/                 # Entidades, Repositórios, Serviços e Logs
│   │   └── infrastructure/         # Implementações e adaptadores de infraestrutura
│   ├── resources/
│   └── pom.xml
│
├── frontend/                       # Aplicação Angular 19
│
├── infra/                          # Infraestrutura Docker e scripts
│   ├── docker-compose.yml          # Sobe PostgreSQL e WildFly
│   ├── Dockerfile
│   ├── enable-ejb-remoting.cli
│   ├── install-datasource.cli
│   └── entrypoint.sh
```

## 🧭 Mapa Visual da Arquitetura em Camadas

```text
┌────────────────────────────────────────────────────────────────────────┐
│                                FRONTEND (Angular 19)                   │
│       Interface web que consome a API REST via HTTP                    │
│                  │                                                     │
│                  ▼                                                     │
│         ┌───────────────────────────────────────────┐                  │
│         │ BACKEND (Spring Boot - Porta 8081)        │                  │
│         │───────────────────────────────────────────│                  │
│         │ Controller → Application → Domain → Infra │                  │
│         │   │                                       │                  │
│         │   ▼                                       │                  │
│         │ Integração com EJB via JNDI remoto        │                  │
│         └───────────────────────────────────────────┘                  │
│                  │                                                     │
│                  ▼                                                     │
│         ┌───────────────────────────────────────────┐                  │
│         │ EJB MODULE (WildFly - Porta 8080)         │                  │
│         │───────────────────────────────────────────│                  │
│         │ Application → Domain → Infrastructure     │                  │
│         │ Serviço de Transferência de Saldo         │                  │
│         └───────────────────────────────────────────┘                  │
│                  │                                                     │
│                  ▼                                                     │
│         ┌───────────────────────────────────────────┐                  │
│         │ POSTGRESQL (Banco de Dados em Docker)     │                  │
│         └───────────────────────────────────────────┘                  │
└────────────────────────────────────────────────────────────────────────┘
```

## ⚙️ Execução do Projeto
### 🧰 Pré-requisitos
- Docker e Docker Compose instalados
- Java 17
- Maven 3.9+
- Node.js 20+ e Angular CLI 19+

## 1️⃣ Subir containers do ambiente
### Na pasta ```infra/```, execute:

```bash
docker compose up -d --build
```

Isso criará:

- Banco de dados PostgreSQL

- Servidor WildFly (bip-wildfly) com EJB configurado

## 2️⃣ Criar usuário no WildFly
### Após o container subir, execute:
```bash
docker exec -it bip-wildfly /opt/jboss/wildfly/bin/add-user.sh -a -u admin -p "Admin#123" --silent
```
## 3️⃣ Fazer build e deploy do módulo EJB
### Na raiz do projeto (bip-teste-integrado):

```bash
 mvn clean install -f ejb-module
 ```
```bash
docker cp ejb-module/target/ejb-module-1.0.0-SNAPSHOT.jar bip-wildfly:/opt/jboss/wildfly/standalone/deployments/
```

Com isso serviço EJB ficara esposto para que o modulo backend faça a integração.

## 4️⃣ Subir o backend Spring Boot
### Na pasta backend-module/:

```bash
mvn clean install
mvn spring-boot:run
```
A API ficará disponível em:
👉 http://localhost:8081

A documentação Swagger/OpenAPI estará em:
👉 http://localhost:8081/swagger-ui.html

## 5️⃣ Subir o frontend Angular
### Na pasta frontend/:

```bash
npm install
ng serve -o
```
O sistema abrirá automaticamente em: 👉 http://localhost:4200

## 💡 Funcionalidades
### 🔹 Backend (Spring Boot)
- CRUD completo de Benefícios
- Serviço de transferência de saldo integrando com o EJB
- Documentação automática com OpenAPI
- Migrations com Flyway
### 🔹 EJB Module (WildFly)
- Serviço remoto via EJB para transações financeiras
- Controle de concorrência otimista
- Registro de logs transacionais
- Camada de domínio desacoplada do backend
### 🔹 Frontend (Angular)
- Tela de listagem de benefícios (editar/deletar)
- Tela de criação de novo benefício
- Tela de transferência de saldo

## 🧩 Comunicação entre Módulos
```
[Angular 4200]
      ↓ (HTTP)
[Spring Boot 8081]
      ↓ (JNDI Remoto)
[WildFly EJB 8080]
      ↓ (JPA)
[PostgreSQL]
```
## 🧪 CI/CD com GitHub Actions
### Arquivo ```.github/workflows/ci.yml```:

## 🧠 Tecnologias Utilizadas
| Camada | Tecnologia | Versão |
| :--- | :--- |:-------|
| Banco de Dados | PostgreSQL | 15     |
| EJB Container | WildFly | 30   |
| EJB Language | Java | 17     |
| Backend | Spring Boot | 3.3.x  |
| Frontend | Angular | 19     |
| ORM | JPA / Hibernate | -      |
| Migrações | Flyway | -      |
| Build | Maven / Node | -      |
| Infra | Docker / Docker Compose | -      |

# 🧾 Licença
### Projeto desenvolvido para fins educacionais e demonstrativos.
© 2025 — Desenvolvido por André Siqueira

