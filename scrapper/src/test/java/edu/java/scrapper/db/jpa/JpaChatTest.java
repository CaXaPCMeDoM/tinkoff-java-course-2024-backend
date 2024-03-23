package edu.java.scrapper.db.jpa;

import edu.java.repository.jpa.JpaChatRepository;
import edu.java.service.jpa.JpaChatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JpaChatTest {
    @InjectMocks
    private JpaChatService jpaChatService;
    @Mock
    private JpaChatRepository chatRepository;

    private static final Long CHAT_ID = -1L;

    @Test
    void registerChatTest() {
        when(chatRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        int sizeAfterAdd = chatRepository.findAll().size();

        jpaChatService.register(CHAT_ID);

        int sizeBeforeAdd = sizeAfterAdd + 1;

        verify(chatRepository, times(1)).save(any());
    }

}
