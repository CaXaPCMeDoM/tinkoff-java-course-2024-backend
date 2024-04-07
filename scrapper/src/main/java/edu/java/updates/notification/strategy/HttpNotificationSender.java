package edu.java.updates.notification.strategy;

import edu.java.internal.client.UpdateClient;
import edu.java.internal.client.dto.LinkClientUpdateRequest;
import org.springframework.stereotype.Service;

@Service
public class HttpNotificationSender implements NotificationSender {
    private final UpdateClient updateClient;

    public HttpNotificationSender(UpdateClient updateClient) {
        this.updateClient = updateClient;
    }

    @Override
    public void sendNotification(LinkClientUpdateRequest message) {
        updateClient.postRepositoryData(message);
    }
}
