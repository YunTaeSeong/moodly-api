FROM eclipse-temurin:21-jre
WORKDIR /app
COPY product-service/build/libs/*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","/app/app.jar"]