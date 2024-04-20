FROM openjdk:21
COPY target/bot.jar /app/bot.jar
WORKDIR /app
EXPOSE 8090
EXPOSE 8091
ENV TELEGRAM_BOT_TOKEN=${TELEGRAM_BOT_TOKEN}
CMD ["java", "-jar", "bot.jar"]
