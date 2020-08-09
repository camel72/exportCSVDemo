FROM openjdk:8-jdk-alpine
ADD target/persondata-0.0.1-SNAPSHOT.jar persondata-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT  ["java","-Dspring.profiles.active=docker","-jar","/persondata-0.0.1-SNAPSHOT.jar"]