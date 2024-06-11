# Stage 1: Build the application
FROM gradle:7.5.1-jdk17 AS BUILD
WORKDIR /app
COPY . .
RUN gradle build -x test

# Stage 2: Create the final image
FROM openjdk:17.0.1-slim
WORKDIR /app
COPY --from=BUILD /app/build/libs/Exam_engine-0.0.1-SNAPSHOT.jar exam-engine.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "exam-engine.jar"]
