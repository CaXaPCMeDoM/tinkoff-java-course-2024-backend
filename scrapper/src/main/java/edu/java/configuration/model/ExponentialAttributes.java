package edu.java.configuration.model;

import lombok.Data;

import java.time.Duration;

@Data
public class ExponentialAttributes {
    private Integer multiplierValue;
    private Duration randomIntervalValue;
    private Boolean randomIntervalOn;
    private Duration maxIntervalValue;
    private Boolean maxIntervalOn;
}
