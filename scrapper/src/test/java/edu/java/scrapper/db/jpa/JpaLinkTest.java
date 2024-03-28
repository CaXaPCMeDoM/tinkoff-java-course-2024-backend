package edu.java.scrapper.db.jpa;

import edu.java.dto.ChatDto;
import edu.java.dto.LinkDto;
import edu.java.dto.ChatLinkId;
import edu.java.dto.ChatLinkDto;
import edu.java.repository.jpa.JpaChatLinkRepository;
import edu.java.repository.jpa.JpaChatRepository;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.service.jpa.JpaLinkService;
import java.net.URI;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JpaLinkTest {
    @InjectMocks
    private JpaLinkService jpaLinkService;
    @Mock
    private JpaLinkRepository jpaLinkRepository;
    @Mock
    private JpaChatLinkRepository jpaChatLinkRepository;
    @Mock
    private JpaChatRepository jpaChatRepository;

    private static final Long CHAT_ID = -1L;
    private static final Long LINK_ID = -2L;
    private static final URI LINK_URI = URI.create("https://edu.tinkoff.ru");

    @Test
    public void addTest() {
        LinkDto linkDto = new LinkDto(CHAT_ID, LINK_URI.toString());
        LinkDto linkDtoWithLinkId = new LinkDto(CHAT_ID, LINK_URI.toString());
        linkDtoWithLinkId.setLinkId(1L);
        ChatDto chatDto = new ChatDto();
        chatDto.setChatId(CHAT_ID);
        Optional<ChatDto> optionalChatDto = Optional.of(chatDto);
        ChatLinkDto chatLinkDto = new ChatLinkDto(new ChatLinkId(chatDto, linkDto));

        when(jpaLinkRepository.save(any(LinkDto.class))).thenReturn(linkDtoWithLinkId);
        when(jpaChatRepository.findById(anyLong())).thenReturn(optionalChatDto);
        when(jpaChatLinkRepository.save(any(ChatLinkDto.class))).thenReturn(chatLinkDto);

        Long result = jpaLinkService.add(CHAT_ID, LINK_URI);

        Assertions.assertEquals(linkDtoWithLinkId.getLinkId(), result);
        verify(jpaLinkRepository, times(1)).save(any(LinkDto.class));
        verify(jpaChatRepository, times(1)).findById(anyLong());
        verify(jpaChatLinkRepository, times(1)).save(any(ChatLinkDto.class));
    }

    @Test
    public void removeTest() {
        LinkDto linkDto = new LinkDto(CHAT_ID, LINK_URI.toString());
        linkDto.setLinkId(LINK_ID);

        when(jpaLinkRepository.findByUrl(anyString())).thenReturn(linkDto);

        jpaLinkService.remove(CHAT_ID, LINK_URI);

        verify(jpaLinkRepository, times(1)).findByUrl(anyString());
        verify(jpaChatLinkRepository, times(1)).deleteByChatIdAndLinkId(anyLong(), anyLong());
    }
}
