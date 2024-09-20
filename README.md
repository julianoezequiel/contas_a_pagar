# Projeto Contas a Pagar

## Descrição

O projeto "Contas a Pagar" é uma aplicação desenvolvida em Java utilizando o Spring Boot. O objetivo da aplicação é gerenciar contas a pagar de forma eficiente, permitindo aos usuários cadastrar, atualizar, listar e importar contas através de um arquivo. A aplicação também oferece a funcionalidade de consultar o total de contas pagas em um intervalo de datas.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **PostgreSQL**
- **JUnit**
- **Mockito**
- **Maven**

## Funcionalidades

- Cadastro de novas contas.
- Atualização de contas existentes.
- Alteração da situação de uma conta.
- Listagem de contas pagas com filtros de data e descrição.
- Importação de contas a partir de um arquivo.
- Autenticação de usuários com JWT.

## Instalação

1. Clone o repositório:
   ```bash
   git clone https://github.com/julianoezequiel/contas_a_pagar.git
2 . Navegue até o diretório do projeto:
  cd contas_a_pagar
3 . Configure o banco de dados PostgreSQL e atualize as configurações no arquivo

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

### 3.  Listagem de Contas
Envie uma requisição GET para /contas com parâmetros opcionais:
```bash
/contas?page=0&size=10&dataVencimentoInicio=2024-01-01&dataVencimentoFim=2024-12-31&descricao=Luz
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

"Nova Situação"

```

### 6. Importação de Contas
Envie uma requisição POST para /contas/importar com um arquivo no corpo da requisição.


## Executando o projeto com Docker

Após realizar o build do projeto com o Maven (`mvn clean install`), você pode executar a aplicação e a base de dados utilizando o Docker. Para isso, execute o seguinte comando:

```bash
docker-compose up --build
```

## Licença
Este projeto é licenciado sob a [MIT License](https://opensource.org/licenses/MIT).

