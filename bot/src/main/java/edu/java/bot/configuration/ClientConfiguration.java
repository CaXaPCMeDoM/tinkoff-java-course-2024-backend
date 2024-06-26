package edu.java.bot.configuration;

import edu.java.bot.backoff.BackOff;
import edu.java.bot.configuration.retry.RetryConfigProperties;
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
    @Value("${scrapper-local-host.base-url}")
    private String scrapperBaseUrl;

    private final RetryConfigProperties retryConfigProperties;

    private final BackOff backOff;

    private final ExchangeFilterFunction exchangeFilterFunction = (request, next) -> retryForClient(request, next, 0);

    public ClientConfiguration(BackOff backOff, RetryConfigProperties retryConfigProperties) {
        this.backOff = backOff;
        this.retryConfigProperties = retryConfigProperties;
    }

    @Bean
    public WebClient scrapperClient(WebClient.Builder builder) {
        return builder
            .baseUrl(scrapperBaseUrl)
            .filter(exchangeFilterFunction)
            .build();
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
