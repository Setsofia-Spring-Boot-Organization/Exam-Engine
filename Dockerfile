FROM springboot/docker:spring-boot-java17

# Set the working directory to /app
WORKDIR /app

# Copy the application code to the working directory
COPY src/ ./src/

# Build the application using Gradle
RUN gradle build

# Copy the built application to the working directory
COPY build/libs/*.jar ./app.jar

# Expose the port the application will run on
EXPOSE 8080

# Run the application when the container starts
CMD ["java", "-jar", "app.jar"]