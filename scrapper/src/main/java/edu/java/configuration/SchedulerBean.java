package edu.java.configuration;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerBean {
    @Value("${app.scheduler.interval}")
    private Duration interval;

    @Bean
    public Duration schedulerInterval() {
        return interval;
    }
}
