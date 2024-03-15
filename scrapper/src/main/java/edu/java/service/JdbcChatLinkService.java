package edu.java.service;

import edu.java.dao.ChatLinkDao;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JdbcChatLinkService implements ChatLinkService {
    private final ChatLinkDao chatLinkDao;

    public JdbcChatLinkService(ChatLinkDao chatLinkDao) {
        this.chatLinkDao = chatLinkDao;
    }

    @Override
    public List<Long> getChatidsByUrlId(Long urlId) {
        return chatLinkDao.getChatIdsByLinkId(urlId);
    }
}
