# desafio-despesas-elton-leite

Projeto de desafio feito por Elton Leite Ayres de Medeiros Borges
## Passos para execução

Para que o projeto seja executado é necessário que:

 - Seja criado um banco chamado 'despesas_elton_leite'
	 - Caso seja necessário deve ser mudado a senha e o usuário do banco em: `\src\main\resources\application.properties`
 - Deve-se executar o `src\main\java\com\borelanjo\despesas\DespesasApplication.java` como uma aplicação java para iniciar a aplicação;
	 - Pode ser importado no Postman o arquivo contido em: `src\main\resources\despesas-elton-leite.postman_collection.json`

## O que foi feito

Foi criado uma aplicação restfull, usando JPA com Implementação Hibernate e o framework Spring. Pode ser testado a aplicação usando as seguintes url;

### POST localhost:8080/account

Usado para criar uma conta.

#### HEADERS
|Content-Type|application/json|
|-|-|

#### BODY
```
{
"accountNumber":123456,
"balance":1000.500
}
```
#### Exemplo de requisição

```
curl --request POST \
  --url http://localhost:8080/account \
  --header 'content-type: application/json' \
  --data '{
"accountNumber":123456,
"balance":1000.500
}'
```

### PATCH localhost:8080/account/123460

Utilizado para atualizar um balanço. O tipo deve ser passado como DECREASE (Despesa) ou INCREASE (Receita) e ele que determina se o valor vai ser positivo ou negativo.

#### HEADERS
|Content-Type|application/json|
|-|-|

#### BODY
```
{
"type":"DECREASE",
"value":1.0
}
```
#### Exemplo de requisição

```
curl --request PATCH \
  --url http://localhost:8080/account/180625 \
  --header 'content-type: application/json' \
  --data '{
"type":"DECREASE",
"value":1.0
}'
```

### GET localhost:8080/account

Retorna um objeto do tipo conta recebendo um número de conta como parâmetro
`localhost:8080/account/123456`

#### HEADERS
|Content-Type|application/json|
|-|-|


#### Exemplo de requisição

```
curl --request GET \
  --url http://localhost:8080/account/123456 \
  --header 'content-type: application/json'
```

### localhost:8080/account/123460/transfer

Realiza uma transferência de uma conta de origem para uma conta de destino

`localhost:8080/account/:sourceAccountNumber/transfer`

#### HEADERS
|Content-Type|application/json|
|-|-|

#### BODY
```
{
"destinationAccountNumber":180626,
"value":5.3
}
```
#### Exemplo de requisição

```
curl --request PUT \
  --url http://localhost:8080/account/180625/transfer \
  --header 'content-type: application/json' \
  --data '{
"destinationAccountNumber":180626,
"value":5.3
}'
```

### GET http://localhost:8080/account/123460/transactionHistory

Mostra o histórico de transações de uma determinada conta.

`http://localhost:8080/account/123460/transactionHistory`

#### HEADERS
|Content-Type|application/json|
|-|-|


#### Exemplo de requisição

```
curl --request GET \
  --url http://localhost:8080/account/123460/transactionHistory
```
