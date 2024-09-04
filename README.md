# Projeto Aplicado III

## Descrição do Projeto

Projeto desenvolvido para a disciplina de Projeto Aplicado III, do curso de Analise e Desenvolvimento de Sistemas do UniSENAI.
A aplicação consiste em um sistema de apontamento de relatórios d eprodução e gerenciamento do mesmo para uma fabrica de lactnicos.


## Integrantes
 - Evandro Alves
 - Gilson Langa
 - Vitória
 - Lucas Jose de Paula


## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Maven
- Docker
- Postgres
- Postman / Insomnia

## Como rodar o projeto

### Pré-requisitos

- Ter o docker instalado na máquina.
- Ter o Java 17 instalado na máquina.
- Ter o maven instalado na máquina.

### Rodando o projeto

Realizar a importação das dependências do projeto no arquivo pom.xml

```bash
mvn clean install

```

Rodar o docker-compose

```bash

docker-compose -f docker-postgres.yml up

```

Rodar o projeto

```bash

mvn spring-boot:run
ou 
clicar no play no na classe ProjetoAplicadoApplication.java

```

### Utilizando o projeto

Para usar a API e conseguir exceutar as requisições, é necessário utilizar um software de requisições HTTP, como Postman ou Insomnia.
Deve-se chamar as rotas descritas no controller, passando os parametros necessários para a execução da mesma.
O endereço padrão é http://localhost:8080

```bash

POST http://localhost:8080/product

body:
{
    "name": "Leite Integral",
    "description": "Leite UHT"    
}

```

