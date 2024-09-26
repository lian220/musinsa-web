# Use the official OpenJDK image as a base
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper files
COPY gradlew .
COPY gradle ./gradle

# Copy the build.gradle.kts and settings.gradle.kts
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Copy the source code
COPY src ./src

# Make the Gradle wrapper executable
RUN chmod +x gradlew

# Build the application
RUN ./gradlew build --no-daemon && ls build/libs  # JAR 파일 확인

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "build/libs/musinsa-web-0.0.1-SNAPSHOT.jar"]

