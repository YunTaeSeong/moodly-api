FROM eclipse-temurin:21-jre
WORKDIR /app
COPY gateway-service/build/libs/*.jar app.jar
EXPOSE 8072
ENTRYPOINT ["java","-jar","/app/app.jar"]