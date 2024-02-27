package edu.java.client.github.client;

import edu.java.client.github.responceDTO.ReposResponce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GitHubClient {
    private final WebClient webClient;

    public GitHubClient(WebClient gitHubWebClient) {
        webClient = gitHubWebClient;
    }

    public Mono<ReposResponce> getRepositoryData(
        @PathVariable String owner,
        @PathVariable String repo
    ) {
        return webClient
            .get()
            .uri(
                "repos/{owner}/{repo}/events",
                owner,
                repo
            )
            .retrieve()
            .bodyToFlux(ReposResponce.class)
            .next();
    }
}
