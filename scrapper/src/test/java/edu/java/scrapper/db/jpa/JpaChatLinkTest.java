package edu.java.scrapper.db.jpa;

import edu.java.repository.jpa.JpaChatLinkRepository;
import edu.java.service.jpa.JpaChatLinkService;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JpaChatLinkTest {
    @InjectMocks
    private JpaChatLinkService jpaChatLinkService;
    @Mock
    private JpaChatLinkRepository jpaChatLinkRepository;

    private static final Long CHAT_ID = -1L;
    private static final Long LINK_ID = -2L;
    private static final URI LINK_URI = URI.create("https://edu.tinkoff.ru");
    @Test
    public void testGetChatIdsByUrlId() {
        List<Long> expectedChatIds = Arrays.asList(CHAT_ID, CHAT_ID - 1L, CHAT_ID - 2L);

        when(jpaChatLinkRepository.findChatIdsByUrlId(anyLong())).thenReturn(expectedChatIds);

        List<Long> result = jpaChatLinkService.getChatIdsByUrlId(LINK_ID);

        Assertions.assertEquals(expectedChatIds, result);
        verify(jpaChatLinkRepository, times(1)).findChatIdsByUrlId(anyLong());
    }

}
