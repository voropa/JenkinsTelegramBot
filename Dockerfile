FROM openjdk:8
ADD target/Telegram-1.0-SNAPSHOT-jar-with-dependencies.jar Telegram-1.0-SNAPSHOT-jar-with-dependencies.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "Telegram-1.0-SNAPSHOT-jar-with-dependencies.jar"]