# Stage 1: Build the application
FROM maven:3.8.1-openjdk-17 AS BUILD
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:21-slim
WORKDIR /app
COPY --from=BUILD /app/target/Exam_engine-0.0.1-SNAPSHOT.jar exam_engine.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "exam_engine.jar"]