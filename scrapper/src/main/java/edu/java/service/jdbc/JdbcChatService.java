package edu.java.service.jdbc;

import edu.java.dao.jdbc.ChatDao;
import edu.java.dto.jdbc.JdbcChatDto;
import edu.java.service.ChatService;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
public class JdbcChatService implements ChatService {
    private final ChatDao chatDao;
    private JdbcChatDto jdbcChatDto;

    public JdbcChatService(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    @Override
    public void register(@NotNull long tgChatId) {
        jdbcChatDto = new JdbcChatDto(tgChatId);
        chatDao.add(jdbcChatDto);
        jdbcChatDto = null;
    }

    @Override
    public void unregister(long tgChatId) {
        chatDao.remove(tgChatId);
    }
}
