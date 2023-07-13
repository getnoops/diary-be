# FROM eclipse-temurin:17-jdk-alpine
# FROM arm64v8/eclipse-temurin:17-jre
FROM eclipse-temurin:17
RUN mkdir /opt/app
COPY build/libs/diary-be-0.0.1-SNAPSHOT.jar /opt/app/app.jar
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]