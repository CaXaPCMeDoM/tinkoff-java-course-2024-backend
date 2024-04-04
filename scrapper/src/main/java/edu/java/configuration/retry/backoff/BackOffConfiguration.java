package edu.java.configuration.retry.backoff;

import edu.java.backoff.BackOff;
import edu.java.backoff.retry.ConstantBackOff;
import edu.java.backoff.retry.ExponentialBackOff;
import edu.java.backoff.retry.LinearBackOff;
import edu.java.configuration.retry.RetryConfigProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RetryConfigProperties.class)
public class BackOffConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = "client.retry", name = "back-off-type", havingValue = "CONSTANT")
    public BackOff constantBackOff(
        RetryConfigProperties retryConfigProperties
    ) {
        return new ConstantBackOff(retryConfigProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "client.retry", name = "back-off-type", havingValue = "LINEAR")
    public BackOff linearBackOff(
        RetryConfigProperties retryConfigProperties
    ) {
        return new LinearBackOff(retryConfigProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "client.retry", name = "back-off-type", havingValue = "EXPONENTIAL")
    public BackOff exponentialBackOff(
        RetryConfigProperties retryConfigProperties
    ) {
        return new ExponentialBackOff(retryConfigProperties);
    }
}
