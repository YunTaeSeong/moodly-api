FROM eclipse-temurin:21-jre
WORKDIR /app
COPY payment-service/build/libs/*.jar app.jar
EXPOSE 8088
ENTRYPOINT ["java","-jar","/app/app.jar"]