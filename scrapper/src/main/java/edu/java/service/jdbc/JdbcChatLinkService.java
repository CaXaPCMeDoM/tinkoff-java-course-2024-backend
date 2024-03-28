package edu.java.service.jdbc;

import edu.java.dao.jdbc.JdbcChatLinkDao;
import edu.java.service.ChatLinkService;
import java.util.List;

public class JdbcChatLinkService implements ChatLinkService {
    private final JdbcChatLinkDao jdbcChatLinkDao;

    public JdbcChatLinkService(JdbcChatLinkDao jdbcChatLinkDao) {
        this.jdbcChatLinkDao = jdbcChatLinkDao;
    }

    @Override
    public List<Long> getChatIdsByUrlId(Long urlId) {
        return jdbcChatLinkDao.getChatIdsByLinkId(urlId);
    }
}
