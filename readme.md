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

- Java 17
- MySQL database 8.0

### 📦 Instalando

- Clone the project with the command (or download the zip from Github):

      git clone link_do_github https://github.com/mtpontes/sistema-estetica-abrantes.git

- Enter the main project directory and run:
    * For Linux: 
    
          ./mvnw clean install -DskipTests


    * For Windows:
          
          mvnw.cmd clean install -DskipTests


    * If you already have Maven installed:
    
          mvn clean install -DskipTests

### 🔎 Details

The application is configured to connect to MySQL via port 3306.

### 🌍 Environment variables:

#### Banco de dados
- `DB_USERNAME`: Default value **root**
- `DB_PASSWORD`: Default value **root**

#### Security
- `JWT_SECRET`: secret used to generate a JWT token. Default value **secret**

##### These settings can also be changed in `application.properties`.

### 🌐 Deploy

The packaged app can be found in the `/target` directory after following the installation procedure.

To run the application use the command:
        
    java -jar package_name.jar

</details>