package edu.java.configuration.retry;

import java.time.Duration;
import java.util.List;
import edu.java.configuration.model.ExponentialAttributes;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "client.retry")
public record RetryConfigProperties(
    Integer maxAttempts,
    List<Integer> status,
    Duration delayBase,
    ExponentialAttributes exponentialAttributes) {
}
