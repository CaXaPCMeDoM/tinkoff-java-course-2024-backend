package edu.java.updates.notification.strategy;

import edu.java.internal.client.dto.LinkClientUpdateRequest;

public interface NotificationSender {
    void sendNotification(LinkClientUpdateRequest message);
}
