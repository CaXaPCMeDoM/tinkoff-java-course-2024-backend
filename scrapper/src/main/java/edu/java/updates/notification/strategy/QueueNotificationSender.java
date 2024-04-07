package edu.java.updates.notification.strategy;

import edu.java.internal.client.dto.LinkClientUpdateRequest;
import edu.java.service.kafka.ScrapperQueueProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueNotificationSender implements NotificationSender {
    private final ScrapperQueueProducer scrapperQueueProducer;

    @Override
    public void sendNotification(LinkClientUpdateRequest message) {
        scrapperQueueProducer.send(message);
    }
}
