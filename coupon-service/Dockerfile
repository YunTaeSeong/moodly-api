FROM eclipse-temurin:21-jre
WORKDIR /app
COPY coupon-service/build/libs/*.jar app.jar
EXPOSE 8087
ENTRYPOINT ["java","-jar","/app/app.jar"]