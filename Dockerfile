FROM eclipse-temurin:17_35-jdk-alpine
ARG JAR=target/*.jar
COPY ${JAR} app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]