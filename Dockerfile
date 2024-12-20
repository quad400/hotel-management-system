FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/bns-0.0.1-SNAPSHOT.jar bns.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "bns.jar"]