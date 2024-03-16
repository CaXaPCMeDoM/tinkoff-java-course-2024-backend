package edu.java.service;

import edu.java.dao.ChatLinkDao;
import java.util.List;
import org.springframework.stereotype.Service;

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
