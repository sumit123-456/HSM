# Build Stage
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

COPY hms-backend/pom.xml .
COPY hms-backend/src ./src

RUN mvn -B -DskipTests clean package

# Run Stage
FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENV PORT=8080

CMD ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
