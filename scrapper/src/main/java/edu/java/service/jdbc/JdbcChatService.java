package edu.java.service.jdbc;

import edu.java.dao.jdbc.ChatDao;
import edu.java.dto.ChatDto;
import edu.java.service.ChatService;
import jakarta.validation.constraints.NotNull;

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
    public Long unregister(long tgChatId) {
        return chatDao.remove(tgChatId);
    }
}
