package edu.java.bot.configuration.model;

import java.time.Duration;
import lombok.Data;

@Data
public class ExponentialAttributes {
    private Integer multiplierValue;
    private Duration randomIntervalValue;
    private Boolean randomIntervalOn;
    private Duration maxIntervalValue;
    private Boolean maxIntervalOn;
}
