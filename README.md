# iFood da Cantina

MVP de aplicacao web para pedidos antecipados na cantina universitaria.

## Stack

- Java 17
- Spring Boot
- Thymeleaf
- Spring Data JPA
- H2 Database
- Maven

## Estrutura

```text
src/main/java/br/com/ifooddacantina/
  config/
  controller/
  model/
  repository/
  service/

src/main/resources/
  static/
  templates/
  application.properties
```

## Como Rodar

Com Maven instalado:

```bash
mvn spring-boot:run
```

A aplicacao ficara disponivel em:

```text
http://localhost:8080
```

Console H2:

```text
http://localhost:8080/h2-console
```

## Banco Local

O projeto usa H2 persistido em arquivo local:

```text
./data/ifood-cantina
```

## Status

Funcionalidades implementadas:

- RF01: visualizar cardapio disponivel.

Ainda nao implementado:

- selecao de itens;
- identificacao do aluno;
- escolha de horario de retirada;
- confirmacao de pedido;
- lista de pedidos da cantina;
- validacao completa de pedido;
- ordenacao de pedidos por horario.
