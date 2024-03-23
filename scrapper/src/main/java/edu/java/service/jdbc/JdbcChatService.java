package edu.java.service.jdbc;

import edu.java.dao.jdbc.JdbcChatDao;
import edu.java.dto.ChatDto;
import edu.java.service.ChatService;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;

public class JdbcChatService implements ChatService {
    private final JdbcChatDao jdbcChatDao;
    private ChatDto chatDto;

    public JdbcChatService(JdbcChatDao jdbcChatDao) {
        this.jdbcChatDao = jdbcChatDao;
    }

    @Transactional
    @Override
    public void register(@NotNull long tgChatId) {
        chatDto = new ChatDto(tgChatId);
        jdbcChatDao.add(chatDto);
        chatDto = null;
    }

    @Transactional
    @Override
    public Long unregister(long tgChatId) {
        return jdbcChatDao.remove(tgChatId);
    }
}
