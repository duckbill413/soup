FROM openjdk:17-alpine
WORKDIR /usr/src/app
COPY ./build/libs/soup-api-0.0.1-SNAPSHOT.jar .
EXPOSE 8080

ENTRYPOINT ["java","-jar", "soup-api-0.0.1-SNAPSHOT.jar"]