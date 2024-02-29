package edu.java.internal.client;

import edu.java.internal.client.dto.LinkClientUpdateRequest;
import edu.java.internal.client.dto.LinkClientUpdateResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UpdateClient {
    private final WebClient webClient;

    public UpdateClient(WebClient botClient) {
        webClient = botClient;
    }

    public Mono<LinkClientUpdateResponse> postRepositoryData(LinkClientUpdateRequest linkClientUpdateRequest) {
        return webClient
            .post()
            .uri("/updates")
            .body(Mono.just(linkClientUpdateRequest), LinkClientUpdateRequest.class)
            .retrieve()
            .bodyToFlux(LinkClientUpdateResponse.class)
            .next();
    }
}
