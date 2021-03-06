FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 9002
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app.jar"]