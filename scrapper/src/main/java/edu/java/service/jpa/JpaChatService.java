package edu.java.service.jpa;

import edu.java.dto.jpa.JpaChatDto;
import edu.java.repository.jpa.JpaChatRepository;
import edu.java.service.ChatService;

public class JpaChatService implements ChatService {
    private JpaChatRepository jpaChatRepository;
    @Override
    public void register(long tgChatId) {
        JpaChatDto jpaChatDto = new JpaChatDto(tgChatId);
        jpaChatRepository.save(jpaChatDto);
    }

    @Override
    public void unregister(long tgChatId) {
        jpaChatRepository.deleteById(tgChatId);
    }
}
