package edu.java.service.jpa;

import edu.java.service.ChatLinkService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JpaChatLinkService implements ChatLinkService {
    @Override
    public List<Long> getChatIdsByUrlId(Long urlId) {
        return null;
    }
}
