FROM eclipse-temurin:21.0.5_11-jdk-alpine
WORKDIR /app
COPY target/uniclub-0.0.1-SNAPSHOT.jar /app/uniclub.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "uniclub.jar"]