package edu.java.shedule;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Duration;

@Component
@EnableScheduling
@ConditionalOnProperty(value = "app.scheduler.enable", havingValue = "true", matchIfMissing = true)
public class LinkUpdaterScheduler {
    private final Duration schedulerInterval;

    public LinkUpdaterScheduler(@Qualifier("schedulerInterval") Duration schedulerInterval) {
        this.schedulerInterval = schedulerInterval;
    }

    @Scheduled(fixedDelayString = "#{@schedulerInterval.toMillis()}")
    public void update() {

        System.out.println("Обновление ссылок...");
    }
}
