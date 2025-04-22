FROM gradle:8.5-jdk21 AS builder
WORKDIR /app
COPY backend/ ./backend
WORKDIR /app/backend
RUN gradle build --no-daemon

FROM openjdk:21-slim
WORKDIR /app
COPY --from=builder /app/backend/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
