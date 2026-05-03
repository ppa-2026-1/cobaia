FROM gradle:8.5-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle clean bootJar

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

