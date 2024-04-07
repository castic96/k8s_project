### Dockerfile
## STAGE 1: BUILD ##
FROM gradle:8.7.0-jdk17 AS builder

WORKDIR /app

COPY . .

RUN gradle clean build

## STAGE 2: RUN ##
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
