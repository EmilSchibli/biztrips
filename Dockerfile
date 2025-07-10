# Multi-stage Dockerfile
# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS backend-build

WORKDIR /app

# Copy the Maven POM and resolve dependencies (layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build the JAR
COPY src src
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=backend-build /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8083

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
