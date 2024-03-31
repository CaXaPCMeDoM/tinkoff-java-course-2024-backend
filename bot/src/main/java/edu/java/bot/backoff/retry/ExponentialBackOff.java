package edu.java.bot.backoff.retry;

import edu.java.bot.backoff.BackOff;
import edu.java.bot.configuration.retry.RetryConfigProperties;
import java.time.Duration;

public class ExponentialBackOff implements BackOff {
    private final RetryConfigProperties retryConfigProperties;

    public ExponentialBackOff(RetryConfigProperties retryConfigProperties) {
        this.retryConfigProperties = retryConfigProperties;
    }

    @Override
    public Duration calculatingTheDelay(int attempt) {
        double randomMultiplied = Math.abs(
            Math.pow(
                retryConfigProperties
                    .exponentialAttributes()
                    .getMultiplierValue(), attempt
            )
        );
        if (retryConfigProperties.exponentialAttributes().getRandomIntervalOn()) {
            randomMultiplied += retryConfigProperties.exponentialAttributes().getRandomIntervalValue().toSeconds();
        }
        if (retryConfigProperties.exponentialAttributes().getMaxIntervalOn()) {
            randomMultiplied =
                randomMultiplied % retryConfigProperties.exponentialAttributes().getMaxIntervalValue().toSeconds();
        }
        return retryConfigProperties.delayBase().multipliedBy((long) randomMultiplied);
    }
}
