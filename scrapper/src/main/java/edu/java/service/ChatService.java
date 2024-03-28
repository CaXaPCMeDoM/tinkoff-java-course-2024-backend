package edu.java.service;

import edu.java.my.exception.ChatNotFoundException;

public interface ChatService {
    void register(long tgChatId);

    Long unregister(long tgChatId) throws ChatNotFoundException;
}
