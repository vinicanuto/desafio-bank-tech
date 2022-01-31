# Case Itaú - API de Transferência Bancária

## Tarefa
Construir uma API REST (JSON) que atenda os requistos

1. Endpoint para cadastrar um cliente, com as seguintes informações: id (único),
   nome, número da conta (único) e saldo em conta;
2. Endpoint para listar todos os clientes cadastrados;
3. Endpoint para buscar um cliente pelo número da conta;
4. Endpoint para realizar transferência entre 2 contas. A conta origem precisa ter
   saldo o suficiente para a realização da transferência e a transferência deve ser
   de no máximo R$ 1000,00 reais;
5. Endpoint para buscar as transferências relacionadas à uma conta, por ordem
   de data decrescente. Lembre-se que transferências sem sucesso também
   devem armazenadas.

## Stack Utilizada
- Java 11
- Maven
- Spring Boot
- Spring Rest
- Spring Data
- H2 database
- Docker

## Pré-requisitos
- Java 11
- Maven


##Instruções

Por padrão a aplicação utiliza a porta 8080.

1. Ao clonar o projeto, abra a pasta "bank-tech" em uma IDE de sua preferência e aguarde a sincronização das dependências,
ou rode um **`mvn install`**
2. Gere o pacote da aplicação com **`mvn clean package`**
3. Na pasta `/target` utilize o comando para executar `java -jar bank-tech-0.0.1-SNAPSHOT.jar`

**Observação:** Caso a porta 8080 esteja em uso, execute o comando a seguir indicando a porta desejada 
`java -jar bank-tech-0.0.1-SNAPSHOT.jar -Dserver.port=**PORTA_AQUI**`


##Open API
A aplicação usa o Swagger a fim de documentar os endpoints e sua utilização.
Para acessar a documentação acesse via browser: `http://<HOST>:<PORTA>/swagger-ui/`

Endereço padrão: **http://localhost:8080/swagger-ui/**


##Docker
É possível rodar esta aplicação em um container. Para isto abra o terminal na raíz do projeto
e rode os comandos:
1. `docker build -t bank-tech`
2. `docker run -p 8080:8080 bank-tech`