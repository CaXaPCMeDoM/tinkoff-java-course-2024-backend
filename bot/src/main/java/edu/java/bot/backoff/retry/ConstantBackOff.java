package edu.java.bot.backoff.retry;

import edu.java.bot.backoff.BackOff;
import edu.java.bot.configuration.retry.RetryConfigProperties;
import java.time.Duration;

public class ConstantBackOff implements BackOff {
    private final RetryConfigProperties retryConfigProperties;

    public ConstantBackOff(RetryConfigProperties retryConfigProperties) {
        this.retryConfigProperties = retryConfigProperties;
    }

    @Override
    public Duration calculatingTheDelay(int attempt) {
        return retryConfigProperties.delayBase();
    }
}
