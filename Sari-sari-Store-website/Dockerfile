# Use official Java 17 image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download dependencies first (for faster rebuilds)
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the Spring Boot app (skipping tests)
RUN ./mvnw clean package -DskipTests

# Expose port 8080
EXPOSE 8080

# Run the generated JAR file
CMD ["java", "-jar", "target/SariSariStoreWebsiteApplication.jar"]
