FROM openjdk:21
COPY target/scrapper.jar app/scrapper.jar
WORKDIR /app
EXPOSE 8080
EXPOSE 8081
ENTRYPOINT [ "java", "org.springframework.boot.loader.launch.JarLauncher" ]
