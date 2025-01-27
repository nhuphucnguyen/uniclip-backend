# Use JRE for runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the pre-built jar from host
COPY build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"] 