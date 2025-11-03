# Set workdir
WORKDIR /app

# Copy project files
COPY .. .

# Give execute permission to mvnw
RUN chmod +x mvnw

# Build the Spring Boot app (skip tests for faster build)
RUN ./mvnw clean package -DskipTests

# Expose port 8080
EXPOSE 8080
