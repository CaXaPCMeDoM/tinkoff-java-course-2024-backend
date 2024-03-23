package edu.java.service.jdbc;

import edu.java.dao.jdbc.ChatLinkDao;
import edu.java.service.ChatLinkService;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public class JdbcChatLinkService implements ChatLinkService {
    private final ChatLinkDao chatLinkDao;

    public JdbcChatLinkService(ChatLinkDao chatLinkDao) {
        this.chatLinkDao = chatLinkDao;
    }

    @Transactional
    @Override
    public List<Long> getChatIdsByUrlId(Long urlId) {
        return chatLinkDao.getChatIdsByLinkId(urlId);
    }
}
