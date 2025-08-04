# Use a lightweight OpenJDK base image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the JAR file built by Maven
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
