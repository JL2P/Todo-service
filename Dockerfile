FROM adoptopenjdk/openjdk11
COPY --from=builder /workspace/build/libs/*.jar app.jar
EXPOSE 9002
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod", "app.jar"]
