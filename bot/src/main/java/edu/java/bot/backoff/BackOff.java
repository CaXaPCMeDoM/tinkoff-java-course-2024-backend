package edu.java.bot.backoff;

import java.time.Duration;

public interface BackOff {
    Duration calculatingTheDelay(int attempt);
}
