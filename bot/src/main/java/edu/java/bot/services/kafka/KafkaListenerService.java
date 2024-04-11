package edu.java.bot.services.kafka;

import edu.java.bot.services.updates.NotificationService;
import edu.java.bot.web.controller.dto.LinkUpdateRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaListenerService {
    private final NotificationService notificationService;

    public KafkaListenerService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "${app.scrapper-topic.name}",
                   groupId = "${kafka.group-id}",
                   containerFactory = "kafkaListenerContainerFactory")
    public void listen(@Payload @Valid LinkUpdateRequest linkUpdateRequest) {
        try {
            log.info("Был вызван kafka listener. Пришли id: \n");
            notificationService.sendUpdate(linkUpdateRequest);
        } catch (Exception ex) {
            log.error("Ошибка в kafka listener {}", ex.getMessage());
        }
    }
}
