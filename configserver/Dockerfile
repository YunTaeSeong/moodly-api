FROM eclipse-temurin:21-jre
WORKDIR /app
COPY configserver/build/libs/*.jar app.jar
EXPOSE 8071
ENTRYPOINT ["java","-jar","/app/app.jar"]