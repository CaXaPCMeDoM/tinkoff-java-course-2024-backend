package edu.java.service.jpa;

import edu.java.repository.jpa.JpaChatLinkRepository;
import edu.java.service.ChatLinkService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JpaChatLinkService implements ChatLinkService {
    private final JpaChatLinkRepository jpaChatLinkRepository;

    public JpaChatLinkService(JpaChatLinkRepository jpaChatLinkRepository) {
        this.jpaChatLinkRepository = jpaChatLinkRepository;
    }

    @Override
    public List<Long> getChatIdsByUrlId(Long urlId) {
        return jpaChatLinkRepository.findAllByChatLinkId_Link_LinkId(urlId);
    }
}
