package edu.java.shedule;

import edu.java.model.LinkData;
import edu.java.shedule.process.LinkProcessService;
import java.time.Duration;
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
    private final LinkData linkData = new LinkData();

    public LinkUpdaterScheduler(
        @Qualifier("schedulerInterval") Duration schedulerInterval,
        LinkProcessService linkProcessService
    ) {
        this.schedulerInterval = schedulerInterval;
        this.linkProcessService = linkProcessService;
    }

    @Scheduled(fixedDelayString = "#{@schedulerInterval.toMillis()}")
    public void update() {
        List<String> links = linkData.getAllLinks();
        for (String link : links) {
            linkProcessService.processLink(link);
        }
    }
}
