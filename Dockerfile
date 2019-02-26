FROM openjdk:8-jdk-alpine

LABEL maintainer="callicoder@gmail.com"

VOLUME /build/tmp

ARG JAR_FILE=build/libs/gs-accessing-data-jpa-0.1.0.jar

ADD ${JAR_FILE} websocket-demo.jar
ADD /application.properties /application.properties
ENTRYPOINT ["java" ,"-Djava.security.egd=file:/dev/./urandom --spring.config.location=classpath:file:/application-properties","-jar","websocket-demo.jar"]
EXPOSE 9080


