
# 💳 Sentry Bank - Sistema Bancário Completo

Bem-vindo ao **Sentry Bank**, um sistema bancário desenvolvido com uma API REST robusta, persistência em banco de dados relacional e uma interface web moderna e responsiva.

Este projeto simula operações bancárias como cadastro de clientes (físicos e jurídicos), gestão de agências, contas bancárias e movimentações financeiras como depósitos, saques e transferências.

---

## 🛠️ Tecnologias Utilizadas

### 🔗 Backend (API)
- **Linguagem:** Java 17+
- **Framework:** Spring Boot
- **Padrão Arquitetural:** MVC (Model-View-Controller)
- **ORM:** Hibernate (JPA)
- **Gerenciamento de Dependências:** Maven
- **Controle de Dados:** Spring Data JPA
- **Validações:** Bean Validation (Jakarta Validation)
- **Documentação:** OpenAPI (Swagger) *(pode ser implementado)*
- **Controle de erros:** Exception Handler Customizado
- **Logs:** SLF4J + Logback
- **Transações:** Gerenciamento com `@Transactional` do Spring

### 🗄️ Banco de Dados
- **SGBD:** MySQL
- **Modelagem:**
  - Entidades: Cliente Físico, Cliente Jurídico, Agência, Conta, Transação
  - Relacionamentos com chave primária e estrangeira
- **Mecanismos de integridade:** Constraints SQL e validações no nível de API

### 🎨 Frontend (Interface Web)
- **Tecnologias:** HTML5, CSS3, JavaScript
- **Estilo:** 
  - Design Responsivo
  - Layout escuro com gradientes e foco em usabilidade
- **Framework CSS:** Próprio (customizado)

---

## 📑 Funcionalidades

### 🧑‍💼 Gestão de Clientes
- Cadastro, alteração e exclusão de **Clientes Físicos** e **Jurídicos**
- Validação de CPF, CNPJ e e-mails únicos

### 🏦 Gestão de Agências
- Cadastro de agências com nome, número e telefone
- Vinculação de contas às agências
- Restrição de remoção caso haja contas vinculadas

### 💳 Gestão de Contas
- Abertura de contas vinculadas às agências
- Tipos de conta: Corrente, Poupança, Salário
- Consulta, atualização e exclusão de contas (com restrições)

### 💰 Movimentações Financeiras
- **Depósitos**
- **Saques**
- **Transferências entre contas**
- Histórico de transações
- Alteração automática dos saldos em cada operação

---

## 🚀 Instalação e Configuração

### 🔥 Pré-requisitos
- Java JDK 17+
- Maven
- MySQL 8+
- IDE (IntelliJ, Eclipse, VSCode ou outra de sua preferência)
- Navegador Web

---

### 📥 Clonando o projeto
```bash
git clone https://github.com/seu-usuario/sentry-bank.git
cd sentry-bank
```

---

### 🔧 Configurando o Banco de Dados

1. No MySQL, crie um banco de dados:
```sql
CREATE DATABASE sentry_bank;
```

2. Configure o acesso no arquivo:

```properties
# src/main/resources/application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/sentry_bank
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

### 🏗️ Build e execução da API

```bash
# Para buildar o projeto
mvn clean install

# Para rodar a aplicação
mvn spring-boot:run
```

A API ficará disponível em:
```
http://localhost:8080
```

---

### 🌐 Acessando a Interface Web

1. Acesse a pasta `/frontend` (se estiver separada).
2. Abra o arquivo `index.html` no navegador.

> ✅ A interface web está conectada via requisições HTTP diretamente na API REST desenvolvida em Spring Boot.

---

## 🔌 Rotas da API (Principais)

| Recurso             | Método | Endpoint                                 | Descrição                          |
|---------------------|--------|-------------------------------------------|-------------------------------------|
| Cliente Físico      | GET    | `/api/clientes-fisicos`                  | Listar clientes físicos             |
|                     | POST   | `/api/clientes-fisicos`                  | Criar cliente físico                |
|                     | PUT    | `/api/clientes-fisicos/fisico/{id}`      | Atualizar cliente físico            |
|                     | DELETE | `/api/clientes-fisicos/fisico/{id}`      | Deletar cliente físico              |
| Cliente Jurídico    | GET    | `/api/clientes-juridicos`                | Listar clientes jurídicos           |
|                     | POST   | `/api/clientes-juridicos`                | Criar cliente jurídico              |
| Agência             | GET    | `/api/agencias`                          | Listar agências                     |
|                     | POST   | `/api/agencias`                          | Criar agência                       |
| Conta               | POST   | `/api/agencias/{idAgencia}/contas`       | Abrir conta para uma agência        |
| Transações          | POST   | `/api/transacoes/transferir`             | Realizar transferência              |
|                     | POST   | `/api/contas/{id}/depositar`             | Realizar depósito                   |
|                     | POST   | `/api/contas/{id}/sacar`                 | Realizar saque                      |

---

## 🔐 Segurança e Validações
- Validação de dados sensíveis (CPF, CNPJ, e-mails únicos)
- Controle de erros customizados
- Transações financeiras protegidas com rollback em caso de falha (`@Transactional`)
- Logs de operações
- Restrições na exclusão de entidades com dependências (agências com contas, contas com transações)

---

## 📊 Estrutura da Base de Dados

- **cliente_fisico** (id, nome, email, senha, cpf, data_nascimento)
- **cliente_juridico** (id, nome, email, senha, cnpj, nome_fantasia, data_criacao)
- **agencia** (id, nome, numero, telefone)
- **conta** (id, id_agencia, numero_conta, tipo_conta, saldo, data_criacao, ativa)
- **transacao** (id, id_conta_origem, id_conta_destino, tipo, valor, data_transacao)

---

## 💡 Melhorias Futuras
- Autenticação com Spring Security + JWT
- Relatórios e dashboards com gráficos
- Integração de PIX e boletos
- Deploy em nuvem (AWS, Azure, Railway, etc.)
- Interface web aprimorada com React ou Angular

---

## 🤝 Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues, sugerir melhorias ou enviar pull requests.

---

## 📝 Licença
Este projeto está sob licença MIT. Veja o arquivo LICENSE para mais detalhes.

---

## ✍️ Autor
- Desenvolvido por **Raul Fernandes | Bruno Henrique | Levy**
- GitHub: [https://github.com/T0tsuK4](https://github.com/seu-usuario)
