package edu.java.client.stackoverflow.client;

import edu.java.client.stackoverflow.DataForRequestStackoverflow;
import edu.java.client.stackoverflow.ParserForStackOverflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/stackoverflow")
public class StackOverflowClient {
    private WebClient webClient;

    @Autowired
    public StackOverflowClient(WebClient stackOverflowWebClient) {
        webClient = stackOverflowWebClient;
    }

    @GetMapping("/question/{questionId}")
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
