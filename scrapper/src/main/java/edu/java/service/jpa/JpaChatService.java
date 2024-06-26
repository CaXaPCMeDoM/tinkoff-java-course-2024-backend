package edu.java.service.jpa;

import edu.java.dto.ChatDto;
import edu.java.repository.jpa.JpaChatRepository;
import edu.java.service.ChatService;

public class JpaChatService implements ChatService {
    private final JpaChatRepository jpaChatRepository;
    public final static Long THE_RECORD_EXISTS = 1L;
    public final static Long THE_RECORD_NOT_EXISTS = 0L;

    public JpaChatService(JpaChatRepository jpaChatRepository) {
        this.jpaChatRepository = jpaChatRepository;
    }

    @Override
    public void register(long tgChatId) {
        ChatDto chatDto = new ChatDto(tgChatId);
        jpaChatRepository.save(chatDto);
    }

    @Override
    public Long unregister(long tgChatId) {
        if (jpaChatRepository.existsById(tgChatId)) {
            jpaChatRepository.deleteById(tgChatId);
            return THE_RECORD_EXISTS;
        } else {
            return THE_RECORD_NOT_EXISTS;
        }
    }
}
