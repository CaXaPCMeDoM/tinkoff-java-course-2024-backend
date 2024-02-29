package edu.java.bot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    @Value("${scrapper-local-host.base-url}")
    private String scrapperBaseUrl;

    @Bean
    public WebClient scrapperClient(WebClient.Builder builder) {
        return builder.baseUrl(scrapperBaseUrl).build();
    }
}
