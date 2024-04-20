FROM openjdk:21
COPY target/bot.jar /app/bot.jar
WORKDIR /app
EXPOSE 8090
EXPOSE 8091
CMD ["java", "-jar", "bot.jar"]
