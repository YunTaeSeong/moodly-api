FROM eclipse-temurin:21-jre
WORKDIR /app
COPY notification-service/build/libs/*.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar","/app/app.jar"]