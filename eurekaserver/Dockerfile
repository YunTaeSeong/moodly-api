FROM eclipse-temurin:21-jre
WORKDIR /app
COPY eurekaserver/build/libs/*.jar app.jar
EXPOSE 8070
ENTRYPOINT ["java","-jar",  "/app/app.jar"]