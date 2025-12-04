# ---- Build Stage ----
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy only pom.xml first (for dependency caching)
COPY hms-backend/pom.xml .

# Download dependencies
RUN mvn -B dependency:resolve dependency:resolve-plugins

# Copy the rest of the source code
COPY hms-backend/src ./src

# Build the project
RUN mvn -B -DskipTests clean package


# ---- Run Stage ----
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy the jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
