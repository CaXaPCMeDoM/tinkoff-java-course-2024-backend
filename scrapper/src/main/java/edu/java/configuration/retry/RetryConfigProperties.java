package edu.java.configuration.retry;

import edu.java.configuration.model.ExponentialAttributes;
import java.time.Duration;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "client.retry")
public record RetryConfigProperties(
    Integer maxAttempts,
    List<Integer> status,
    Duration delayBase,
    ExponentialAttributes exponentialAttributes) {
}
