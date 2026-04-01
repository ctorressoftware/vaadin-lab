# Stage 1: build
FROM maven:3.9.14-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests


# Stage 2: runtime
FROM eclipse-temurin:21-jre-ubi10-minimal
WORKDIR /app
RUN useradd -r -u 1001 appuser
COPY --from=builder /app/target/app.jar app.jar
RUN chown appuser:appuser app.jar
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]