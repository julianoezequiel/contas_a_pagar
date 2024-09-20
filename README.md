# Projeto Contas a Pagar

## Descrição

O projeto "Contas a Pagar" é uma aplicação desenvolvida em Java, utilizando o framework Spring Boot, com o propósito de demonstrar o uso de tecnologias modernas e padrões de arquitetura de software. Ele oferece um sistema para gerenciar de forma eficiente o ciclo de vida de contas a pagar, desde o cadastro até a consulta de valores pagos em determinado período.

A aplicação foi projetada seguindo boas práticas de desenvolvimento, como DDD (Domain-Driven Design) e injeção de dependências. Ela utiliza uma arquitetura de APIs RESTful, garantindo segurança e escalabilidade através da implementação de JWT (JSON Web Token) para autenticação e autorização dos usuários.

## Tecnologias e Padrões Utilizados:

- **Java 17: Linguagem de programação robusta e amplamente utilizada no mercado corporativo.**
- **Spring Boot: Framework para simplificar o desenvolvimento e a configuração da aplicação.**
- **Spring Security: Para autenticação e autorização com JWT.**
- **Docker: Facilita a criação de ambientes de desenvolvimento consistentes e isolados.**
- **PostgreSQL: Banco de dados relacional utilizado para armazenar as contas a pagar.**
- **Flyway: Ferramenta de migração de banco de dados para controle de versões de schema.**
- **JPA/Hibernate: Para mapeamento objeto-relacional e persistência de dados.**
- **JUnit: Framework de testes utilizado para garantir a qualidade e integridade do código.**
- **Mockito: Ferramenta para criação de mocks e simulação de comportamentos em testes unitários, permitindo a validação de integrações entre os componentes da aplicação.**

## Funcionalidades principais:

- Cadastro de Contas: Os usuários podem cadastrar novas contas com detalhes como descrição, valor e data de vencimento.
- Atualização de Contas: Permite atualizar os dados das contas existentes.
- Alteração de Situação: Altera o status de uma conta (paga, pendente, etc.).
- Listagem e Consulta de Contas: A aplicação oferece a possibilidade de listar todas as contas pagas ou pendentes com filtros por data e descrição.
- Importação de Contas via Arquivo: É possível importar contas em massa através de arquivos.
- Consulta de Total Pago: Realiza a consulta do valor total pago em um intervalo de datas definido pelo usuário.

## Instalação

### 1. Clone o repositório:
   ```bash
   git clone https://github.com/julianoezequiel/contas_a_pagar.git
```
### 2. Navegue até o diretório do projeto:
  cd contas_a_pagar
### 3. Configure o banco de dados PostgreSQL e atualize as configurações no arquivo

## Uso

### 1. Autenticação
Todos os endpoints da API são autenticados. Antes de acessar qualquer endpoint, você deve autenticar-se usando o endpoint `/authenticate` para obter o token JWT.

- Envie uma requisição POST para `/authenticate` com um JSON contendo `username` e `password`:


```json
{
  "username": "seu_usuario",
  "password": "sua_senha"
}
```

### 2. Cadastro de Conta
Envie uma requisição POST para /contas com os dados da conta:

```json
{
  "descricao": "Conta de Luz",
  "dataVencimento": "2024-10-05",
  "valor": 100.00
}
```

### 3. Listar Contas com Paginação e Filtros
Para listar as contas, você pode utilizar o endpoint `GET /contas`, que aceita parâmetros de paginação e filtros opcionais como data de vencimento e descrição.

#### Exemplo de requisição para listar contas:

```bash
GET /contas?page=0&size=10&dataVencimentoInicio=2024-01-01&dataVencimentoFim=2024-12-31&descricao=a
Authorization: Bearer SEU_TOKEN_JWT
```

### 4.Alteração de Conta

Para alterar as informações de uma conta existente, utilize o endpoint `PUT /contas/{id}`. O `{id}` deve ser o identificador da conta que deseja atualizar.

#### Exemplo de requisição para alterar uma conta:

```bash
PUT /contas/{id}
Authorization: Bearer SEU_TOKEN_JWT
Content-Type: application/json

{
  "descricao": "Nova descrição da conta",
  "valor": 500.00,
  "dataVencimento": "2024-12-31"
}
```

### 5. Alteração de Situação da Conta

Para alterar a situação de uma conta (por exemplo, "Paga", "Atrasada", etc.), utilize o endpoint PATCH /contas/{id}/situacao. O {id} é o identificador da conta, e a nova situação deve ser enviada no corpo da requisição.

Exemplo de requisição para alterar a situação de uma conta:
```bash
PATCH /contas/{id}/situacao
Authorization: Bearer SEU_TOKEN_JWT
Content-Type: application/json

{
  "situacao": "Nova Situação"
}

```
Lembre-se de substituir "Nova Situação" pelo novo status desejado, como "Paga" ou "Atrasada". Além disso, o token JWT deve ser incluído no cabeçalho de autorização para garantir que a requisição seja autenticada corretamente.

### 6. Obter o Valor Total Pago

Para obter o valor total pago de contas dentro de um intervalo de datas, utilize o endpoint `GET /contas/total-pago`. Nesse endpoint, você deve informar as datas de início e fim do intervalo como parâmetros de consulta.

#### Exemplo de requisição para obter o valor total pago:

```bash
GET /contas/total-pago?dataInicio=2024-01-01&dataFim=2024-12-31
Authorization: Bearer SEU_TOKEN_JWT
```
Exemplo de resposta:
```bash
{
  "valorTotalPago": 1500.00
}
```
Essa requisição retorna o valor total pago no período especificado. Certifique-se de enviar o token JWT no cabeçalho da requisição para autenticação.

### 7. Importação de Contas
Envie uma requisição POST para /contas/importar com um arquivo no corpo da requisição.

Exemplo de resposta:
```json
{
    "message": "Contas importadas com sucesso!",
    "status": 200
}
```

## Observações
As datas devem ser informadas no formato YYYY-MM-DD

## Executando o projeto com Docker

Após realizar o build do projeto com o Maven (`mvn clean install`), você pode executar a aplicação e a base de dados utilizando o Docker. Para isso, execute o seguinte comando:

```bash
docker-compose up --build
```

## Licença
Este projeto é licenciado sob a [MIT License](https://opensource.org/licenses/MIT).

