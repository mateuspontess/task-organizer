## 🖥️ About the project

This is a complete example of Clean Architecture implemented with Java, SpringBoot and MongoDB, in a simple CRUD application, with high coverage of unit and integration tests, using Junit 5 and TestContainers.

## 🛠️ Tecnologies 
- [Spring Boot](https://spring.io/projects/spring-boot)
- [MongoDB](https://www.mongodb.com/)
- [Docker](https://www.docker.com/)
- [TestContainers](https://testcontainers.com/)
- [Java JWT (Auth0)](https://github.com/auth0/java-jwt)
- [Lombok](https://projectlombok.org/)
- [Springdoc OpenAPI](https://springdoc.org/)

## 🚀 How to Run
<details>
<summary>Clique para expandir</summary>

### 📋 Prerequisites

- Docker
- Docker Compose

### 🔎 Details

The application is configured to connect to MongoDB via port 27017.

### 🌍 Environment variables:

docker-compose.yml is configured to use default values.

#### Database
`DB_USERNAME`: Default value **root**

`DB_PASSWORD`: Default value **root**

#### Security
`JWT_SECRET`: secret used to generate a JWT token. Default value **secret**

##### These settings can also be changed in `application.properties`.

### 📦 Installing

Clone the project with the command (or download the zip from Github):

      git clone https://github.com/mtpontes/task-organizer-api.git

Build the application:

      docker run --rm --workdir /app -v ${PWD}:/app maven:3.6.3-openjdk-17-slim mvn clean install -DskipTests

### 🌐 Deploy

Raise the containers:

      docker-compose up --build

</details>

## 📖 Documentation

The documentation can be accessed after deploying the application via the URL http://localhost:8080/swagger-ui/index.html#/ .

You can also import my set of requests into Postman. There you have all the endpoints with all the necessary URL parameters and body details to interact with the API.

[<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://app.getpostman.com/run-collection/31232249-ca8cfa3f-f3e7-4ab3-a595-dd7faca07dbe?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D31232249-ca8cfa3f-f3e7-4ab3-a595-dd7faca07dbe%26entityType%3Dcollection%26workspaceId%3Daae15406-ac2a-4087-8c9e-47072e8aa119)
