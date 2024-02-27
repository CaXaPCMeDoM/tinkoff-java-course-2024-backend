package edu.java;

import edu.java.configuration.ApplicationConfig;
import java.net.MalformedURLException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class ScrapperApplication {
    public static void main(String[] args) throws MalformedURLException {
        ConfigurableApplicationContext context = SpringApplication.run(ScrapperApplication.class, args);
    }
}
