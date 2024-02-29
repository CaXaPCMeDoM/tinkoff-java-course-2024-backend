package edu.java.bot.web.client;

import edu.java.bot.web.client.dto.link.AddRequest;
import edu.java.bot.web.client.dto.link.GetLinksResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.net.URI;

@Component
public class LinkClient {
    private final WebClient webClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatClient.class);

    public LinkClient(WebClient scrapperClient) {
        webClient = scrapperClient;
    }

    public void postAddTrackingLink(Long id, URI link) {
        AddRequest addRequest = new AddRequest();
        addRequest.setLink(link);
        webClient
            .post()
            .uri("/links")
            .header("Tg-Chat-Id", String.valueOf(id))
            .body(Mono.just(addRequest), AddRequest.class)
            .exchangeToMono(response -> {
                LOGGER.info(
                    "Response status: {} {} \n Добавлена ссылка: {} \n id: {}",
                    response.statusCode(),
                    getClass(),
                    addRequest.getLink(),
                    id
                );
                return Mono.empty();
            })
            .subscribe();
    }

    public Mono<GetLinksResponse> getLinksById(Long id) {
        return webClient
            .get()
            .uri("/links")
            .header("Tg-Chat-Id", String.valueOf(id))
            .retrieve()
            .bodyToMono(GetLinksResponse.class)
            .doOnNext(response -> LOGGER.info(
                "Вернулись ссылки для id: {}",
                id
            ));
    }

    public void deleteTrackingLink(Long id, URI uri) {
        AddRequest addRequest = new AddRequest();
        addRequest.setLink(uri);
        webClient
            .method(HttpMethod.DELETE)
            .uri("/links")
            .header("Tg-Chat-Id", String.valueOf(id))
            .body(Mono.just(addRequest), AddRequest.class)
            .exchangeToMono(response -> {
                LOGGER.info(
                    "Response status: {} {} \n Удалена ссылка: {} \n id: {}",
                    response.statusCode(),
                    getClass(),
                    addRequest.getLink(),
                    id
                );
                return Mono.empty();
            })
            .subscribe();
    }
}
