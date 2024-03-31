package edu.java.configuration;

import edu.java.backoff.BackOff;
import edu.java.configuration.retry.RetryConfigProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@EnableCaching
@Configuration
public class ClientConfiguration {
    @Value("${github.base-url}")
    private String gitHubBaseUrl;

    @Value("${stackoverflow.base-url}")
    private String stackoverflowBaseUrl;

    @Value("${bot-local-host.base-url}")
    private String localHostBaseUrl;

    private final RetryConfigProperties retryConfigProperties;
    private final BackOff backOff;

    private final ExchangeFilterFunction exchangeFilterFunction = (request, next) -> {
        return retryForClient(request, next, 0);
    };

    public ClientConfiguration(RetryConfigProperties retryConfigProperties, BackOff backOff) {
        this.retryConfigProperties = retryConfigProperties;
        this.backOff = backOff;
    }

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

    private Mono<ClientResponse> retryForClient(ClientRequest request, ExchangeFunction next, Integer attemptNumber) {
        return next.exchange(request)
            .flatMap(clientResponse -> {
                if (retryConfigProperties.status().contains(clientResponse.statusCode().value())
                    && attemptNumber < retryConfigProperties.maxAttempts()) {
                    return Mono.delay(backOff.calculatingTheDelay(attemptNumber))
                        .then(Mono.defer(() -> retryForClient(request, next, attemptNumber + 1)));
                }
                return Mono.just(clientResponse);
            });
    }
}
