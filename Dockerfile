# Stage 1: Build the application
FROM gradle:7.5.1-jdk17 AS BUILD
WORKDIR /app

# Copy only the Gradle wrapper and build script first for better caching
COPY gradle /app/gradle
COPY gradlew /app/gradlew
COPY build.gradle.kts /app/
COPY settings.gradle.kts /app/

# Grant execute permissions for the gradlew script (fix)
RUN chmod +x /app/gradlew

# Download dependencies
RUN ./gradlew build --no-daemon --stacktrace || return 0

# Copy the rest of the project files
COPY . /app

# Package the application
RUN ./gradlew clean build -x test --no-daemon

# Stage 2: Create the final image
FROM openjdk:21-slim
WORKDIR /app

# Fix potential caching issue (optional)
COPY gradlew /app/gradlew
RUN chmod +x /app/gradlew  # Reset permissions (optional)

# Copy the JAR from the build stage
COPY --from=BUILD /build/libs/Exam_engine-0.0.1-SNAPSHOT.jar exam_engine.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "exam_engine.jar"]