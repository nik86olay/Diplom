FROM openjdk:18-alpine

EXPOSE 9090

ADD target/diplom-0.0.1-SNAPSHOT.jar diplom.jar

ENTRYPOINT ["java", "-jar", "diplom.jar"]

