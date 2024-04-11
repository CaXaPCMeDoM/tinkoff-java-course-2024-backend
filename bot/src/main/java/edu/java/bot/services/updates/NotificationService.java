package edu.java.bot.services.updates;

import edu.java.bot.services.bot.Bot;
import edu.java.bot.web.controller.dto.LinkUpdateRequest;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final Bot bot;

    public NotificationService(Bot bot) {
        this.bot = bot;
    }

    public void sendUpdate(LinkUpdateRequest linkUpdateRequest) {
        for (Long chatId : linkUpdateRequest.getTgChatIds()) {
            bot.sendAMessageAboutUpdatingLinks(chatId, linkUpdateRequest);
        }
    }
}
