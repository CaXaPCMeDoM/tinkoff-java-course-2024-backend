package edu.java.internal.client;

import edu.java.ApiErrorResponse;
import edu.java.internal.client.dto.LinkClientUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UpdateClient {
    private final WebClient webClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateClient.class);

    public UpdateClient(WebClient botClient) {
        webClient = botClient;
    }

    public void postRepositoryData(LinkClientUpdateRequest linkClientUpdateRequest) {
        LOGGER.info("Request to be sent: {}", linkClientUpdateRequest);

        Mono<ApiErrorResponse> mono = webClient
            .post()
            .uri("/updates")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(linkClientUpdateRequest), LinkClientUpdateRequest.class)
            .retrieve()
            .bodyToFlux(ApiErrorResponse.class)
            .next();

        mono.subscribe(response -> LOGGER.info("Response received: {}", response));
    }
}

