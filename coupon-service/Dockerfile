FROM eclipse-temurin:21-jre
EXPOSE 8087
WORKDIR /app
COPY coupon-service/build/libs/*.jar app.jar
ENTRYPOINT["java","-jar","/app/app.jar"]
