# Stage 1: Build the application
FROM openjdk:21-jdk-slim AS BUILD

# Install Gradle
ENV GRADLE_VERSION=7.5.1
RUN apt-get update && apt-get install -y wget unzip && \
    wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip && \
    unzip gradle-${GRADLE_VERSION}-bin.zip && \
    mv gradle-${GRADLE_VERSION} /opt/gradle && \
    ln -s /opt/gradle/bin/gradle /usr/bin/gradle && \
    rm gradle-${GRADLE_VERSION}-bin.zip

WORKDIR /app
COPY . .
RUN gradle build -x test

# Stage 2: Create the final image
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=BUILD /app/build/libs/Exam_engine-0.0.1-SNAPSHOT.jar exam-engine.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "exam-engine.jar"]