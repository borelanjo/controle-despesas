FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD /target/despesas.jar service.jar

ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /service.jar" ]