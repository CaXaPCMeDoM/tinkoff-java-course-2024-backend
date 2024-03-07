package edu.java.external.client.stackoverflow.client;

import edu.java.external.client.stackoverflow.DataForRequestStackoverflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class StackOverflowClient {
    private WebClient webClient;

    @Autowired
    public StackOverflowClient(WebClient stackOverflowWebClient) {
        webClient = stackOverflowWebClient;
    }

    public Mono<DataForRequestStackoverflow> fetchQuestion(@PathVariable String questionId) {

        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/questions/{questionId}")
                .queryParam("site", "stackoverflow")
                .build(questionId))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(DataForRequestStackoverflow.class);
    }
}
