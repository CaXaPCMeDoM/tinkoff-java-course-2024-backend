package edu.java.shedule;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@ConditionalOnProperty(value = "app.scheduler.enable", havingValue = "true", matchIfMissing = true)
public class LinkUpdaterScheduler {
    private final Duration schedulerInterval;
    private final LinkProcessService linkProcessService;

    public LinkUpdaterScheduler(
        @Qualifier("schedulerInterval") Duration schedulerInterval,
        LinkProcessService linkProcessService
    ) {
        this.schedulerInterval = schedulerInterval;
        this.linkProcessService = linkProcessService;
    }

    @Scheduled(fixedDelayString = "#{@schedulerInterval.toMillis()}")
    public void update() {
        List<String> links = Arrays.asList(
            "https://github.com/sanyarnd/java-course-2023-backend-template",
            "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c-c",
            "https://stackoverflow.com",
            "https://github.com/CaXaPCMeDoM/TestRep"
        );
        for (String link : links) {
            linkProcessService.processLink(link);
        }
    }
}
