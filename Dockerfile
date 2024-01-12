FROM maven:3.9.4-amazoncorretto-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN  mvn clean install -DskipTests

#ENV SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME}
#ENV SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}

FROM openjdk:21
MAINTAINER MinhDunk
EXPOSE 8080
WORKDIR /app
COPY --from=build app/target/research-api-0.0.1-SNAPSHOT.jar research-api.jar
LABEL authors="MinhDunk"

ENTRYPOINT ["java", "-jar", "research-api.jar"]