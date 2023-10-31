FROM openjdk:18-alpine

EXPOSE 9090

ARG JAR_FILE=target/Diplom-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

COPY ${JAR_FILE} diplom.jar

ENTRYPOINT ["java", "-jar", "diplom.jar"]

