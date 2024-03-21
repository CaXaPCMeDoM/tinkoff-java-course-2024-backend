package edu.java.service.jdbc;

import edu.java.dao.jdbc.ChatLinkDao;
import java.util.List;
import edu.java.service.ChatLinkService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class JdbcChatLinkService implements ChatLinkService {
    private final ChatLinkDao chatLinkDao;

    public JdbcChatLinkService(ChatLinkDao chatLinkDao) {
        this.chatLinkDao = chatLinkDao;
    }

    @Override
    public List<Long> getChatIdsByUrlId(Long urlId) {
        return chatLinkDao.getChatIdsByLinkId(urlId);
    }
}
