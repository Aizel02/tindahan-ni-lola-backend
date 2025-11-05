# Use the official OpenJDK 17 image
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy Maven build output (the JAR file)
COPY target/*.jar app.jar

# Expose the port (must match your Spring Boot server.port)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
