# syntax=docker/dockerfile:1

FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos primero archivos de build para aprovechar caché
COPY pom.xml ./
COPY mvnw ./
COPY .mvn ./.mvn

# Evita problemas si mvnw viene con CRLF desde Windows
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw

# (Opcional) Descarga dependencias antes de copiar src para mejorar caché
RUN ./mvnw -B -q dependency:go-offline -DskipTests || true

# Código fuente
COPY src ./src

# Build (sin tests)
RUN ./mvnw -B clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar

ENV JAVA_OPTS=""
EXPOSE 8080

# Railway puede inyectar PORT; tu app también debe leerlo (server.port=${PORT:8080})
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]