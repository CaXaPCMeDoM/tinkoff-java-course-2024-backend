package edu.java.service;

import edu.java.dao.ChatLinkDao;
import java.util.List;
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
    public List<Long> getChatidsByUrlId(Long urlId) {
        return chatLinkDao.getChatIdsByLinkId(urlId);
    }
}
