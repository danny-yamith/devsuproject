FROM openjdk:11

ARG JAR_FILE=target/*.jar

COPY ./target/AccountMoveService-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar" , "/app.jar"]

EXPOSE 8083


