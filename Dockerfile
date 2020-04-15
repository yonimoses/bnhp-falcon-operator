FROM openjdk:8-jdk-alpine
COPY target/test.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","/test.jar"]