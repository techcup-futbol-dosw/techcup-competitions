# syntax=docker/dockerfile:1.7

# Build stage
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml .
COPY src ./src

RUN mvn -B -DskipTests package

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Runtime defaults for microservice integration and JWT validation.
ENV SERVER_PORT=8083 \
    API_GATEWAY_URL=http://api-gateway:8080 \
    FRONTEND_URL=http://frontend:3000 \
    JWT_SECRET=change-this-in-runtime \
    JWT_ISSUER=techcup-auth \
    JWT_AUDIENCE=techcup-services \
    JWT_ROLES_CLAIM=roles \
    JWT_ROLE_CAPITAN=CAPITAN \
    JWT_ROLE_JUGADOR=JUGADOR

COPY --from=build /workspace/target/*.jar /app/app.jar

EXPOSE 8083

RUN addgroup -S spring && adduser -S spring -G spring
USER spring

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
