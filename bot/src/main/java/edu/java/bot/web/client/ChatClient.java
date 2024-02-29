package edu.java.bot.web.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ChatClient {
    private final WebClient webClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatClient.class);

    public ChatClient(WebClient scrapperClient) {
        webClient = scrapperClient;
    }

    public void postRegisterChat(Long id) {
        webClient
            .post()
            .uri("/tg-chat/{id}", id)
            .exchangeToMono(response -> {
                LOGGER.info("Response status: {} {} регистрация id чата. Id: {}", response.statusCode(), getClass(), id);
                return Mono.empty();
            })
            .subscribe();
    }

    public void deleteChat(Long id) {
        webClient
            .method(HttpMethod.DELETE)
            .uri("/tg-chat/{id}", id)
            .exchangeToMono(clientResponse -> {
                LOGGER.info("Response status: {} удаление чата", clientResponse.statusCode(), getClass());
                return Mono.empty();
            })
            .subscribe();
    }
}
