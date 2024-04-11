package edu.java.updates.notification;

import edu.java.configuration.ApplicationConfig;
import edu.java.internal.client.dto.LinkClientUpdateRequest;
import edu.java.updates.notification.strategy.HttpNotificationSender;
import edu.java.updates.notification.strategy.NotificationSender;
import edu.java.updates.notification.strategy.QueueNotificationSender;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    private final QueueNotificationSender queueNotificationSender;
    private final HttpNotificationSender httpNotificationSender;
    private final ApplicationConfig config;
    private NotificationSender notificationSender;

    @PostConstruct
    public void init() {
        notificationSender = config.useQueue() ? queueNotificationSender : httpNotificationSender;
    }

    public void sender(LinkClientUpdateRequest message) {
        try {
            notificationSender.sendNotification(message);
        } catch (Exception ex) {
            log.error("Ошибка в NotificationService метод sender. Сообщение ошибки: {}", ex.getMessage());
        }
    }
}
