package edu.java.internal.client;

import edu.java.ApiErrorResponse;
import edu.java.internal.client.dto.LinkClientUpdateRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UpdateClient {
    private final WebClient webClient;

    public UpdateClient(WebClient botClient) {
        webClient = botClient;
    }

    public Mono<ApiErrorResponse> postRepositoryData(LinkClientUpdateRequest linkClientUpdateRequest) {
        return webClient
            .post()
            .uri("/updates")
            .body(Mono.just(linkClientUpdateRequest), LinkClientUpdateRequest.class)
            .retrieve()
            .bodyToFlux(ApiErrorResponse.class)
            .next();
    }
}
