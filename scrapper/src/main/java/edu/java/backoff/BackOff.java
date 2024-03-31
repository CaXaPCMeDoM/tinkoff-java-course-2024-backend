package edu.java.backoff;

import java.time.Duration;

public interface BackOff {
    Duration calculatingTheDelay(int attempt);
}
