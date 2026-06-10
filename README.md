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

Esta etapa contem apenas a estrutura base do projeto. As funcionalidades de cardapio, pedidos e gestao da cantina ainda nao foram implementadas.
