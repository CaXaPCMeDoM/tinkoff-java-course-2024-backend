package edu.java.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

@Configuration
public class SchedulerBean {
    @Value("${app.scheduler.interval}")
    private Duration interval;

    @Bean
    public Duration schedulerInterval() {
        return interval;
    }
}
