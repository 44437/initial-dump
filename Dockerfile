FROM openjdk:22-jdk

WORKDIR /app
COPY target/initial-dump.jar /app/initial-dump.jar

ENTRYPOINT ["java", "-jar", "initial-dump.jar"]
