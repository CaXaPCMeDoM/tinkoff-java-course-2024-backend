package edu.java.service.decorator;

import edu.java.my.exception.ChatNotFoundException;
import edu.java.service.ChatService;

public class ValueToBeDeletedExistsDecorator implements ChatService {
    private final ChatService chatService;

    public ValueToBeDeletedExistsDecorator(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public void register(long tgChatId) {
        chatService.register(tgChatId);
    }

    @Override
    public Long unregister(long tgChatId) throws ChatNotFoundException {
        Long result = chatService.unregister(tgChatId);
        if (result == 0L) {
            throw new ChatNotFoundException(tgChatId);
        }
        return result;
    }
}
