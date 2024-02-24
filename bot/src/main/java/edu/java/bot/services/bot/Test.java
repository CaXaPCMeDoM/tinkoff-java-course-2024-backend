package edu.java.bot.services.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequestMapping(value = "/events", produces = "application/json")
public class Test {
    private final WebClient baseUrl;

    @Autowired
    public Test(WebClient gitHubWebClient) {
        baseUrl = gitHubWebClient;
    }

    @GetMapping
    public Flux<Boss> getRepositoryData(String owner, String repo) {
        return baseUrl
            .get()
            .uri("repos/{owner}/{repo}/events", owner, repo)
            .retrieve()
            .bodyToFlux(Boss.class);
    }

}
