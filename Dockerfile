# -------- Stage 1: Build the app using Maven --------
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set working directory inside builder container
WORKDIR /app

# Copy your project files (everything except ignored ones)
COPY . .

# Build the application, skipping tests to speed up build
RUN mvn clean package -DskipTests

# -------- Stage 2: Run the app in a lightweight image --------
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside runtime container
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose port (adjust if your Spring Boot app uses a different port)
EXPOSE 8080

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
