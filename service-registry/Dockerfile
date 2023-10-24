FROM openjdk:11

ARG JAR_FILE=target/*.jar

COPY ./target/service-registry-0.0.1.jar app.jar

ENTRYPOINT ["java", "-jar" , "/app.jar"]

EXPOSE 8761