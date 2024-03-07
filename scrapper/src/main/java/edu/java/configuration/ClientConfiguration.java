package edu.java.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    @Value("${github.base-url}")
    private String gitHubBaseUrl;

    @Value("${stackoverflow.base-url}")
    private String stackoverflowBaseUrl;

    @Value("${bot-local-host.base-url}")
    private String localHostBaseUrl;

    @Bean
    public WebClient gitHubWebClient(WebClient.Builder builder) {
        return builder.baseUrl(gitHubBaseUrl).build();
    }

    @Bean
    public WebClient stackOverflowWebClient(WebClient.Builder builder) {
        return builder.baseUrl(stackoverflowBaseUrl).build();
    }

    @Bean
    public WebClient botClient(WebClient.Builder builder) {
        return builder.baseUrl(localHostBaseUrl).build();
    }
}
