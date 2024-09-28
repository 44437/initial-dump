FROM openjdk:22-jdk

WORKDIR /app
COPY target/initial-dump.jar /app/initial-dump.jar

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "initial-dump.jar"]
