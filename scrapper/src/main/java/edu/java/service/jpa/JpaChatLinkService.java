package edu.java.service.jpa;

import edu.java.repository.jpa.JpaChatLinkRepository;
import edu.java.service.ChatLinkService;
import java.util.List;

public class JpaChatLinkService implements ChatLinkService {
    private final JpaChatLinkRepository jpaChatLinkRepository;

    public JpaChatLinkService(JpaChatLinkRepository jpaChatLinkRepository) {
        this.jpaChatLinkRepository = jpaChatLinkRepository;
    }

    @Override
    public List<Long> getChatIdsByUrlId(Long urlId) {
        return jpaChatLinkRepository.findChatIdsByUrlId(urlId);
    }
}
