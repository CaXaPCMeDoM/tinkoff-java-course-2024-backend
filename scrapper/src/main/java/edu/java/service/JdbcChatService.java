package edu.java.service;

import edu.java.dao.ChatDao;
import edu.java.dao.dto.ChatDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class JdbcChatService implements ChatService {
    private final ChatDao chatDao;
    private ChatDto chatDto;

    public JdbcChatService(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    @Override
    public void register(@NotNull long tgChatId) {
        chatDto = new ChatDto(tgChatId);
        chatDao.add(chatDto);
        chatDto = null;
    }

    @Override
    public void unregister(long tgChatId) {
        chatDao.remove(tgChatId);
    }
}
