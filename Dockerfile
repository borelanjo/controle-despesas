FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD /target/despesas.jar service.jar
ENTRYPOINT ["java","-jar","service.jar"]