# Start from an OpenJDK base image
FROM openjdk:21-jdk-slim

# Add a volume to store logs or temp data
VOLUME /tmp

# Set working directory
WORKDIR /app

# Copy the Spring Boot jar into the container
COPY target/*.jar web.jar

# Expose the application port
EXPOSE 8081

# Run the jar file
ENTRYPOINT ["java", "-jar", "web.jar"]
