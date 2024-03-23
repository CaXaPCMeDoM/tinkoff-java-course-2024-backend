package edu.java.scrapper.db.jpa;

import edu.java.dto.ChatDto;
import edu.java.dto.LinkDto;
import edu.java.dto.jpa.ChatLinkId;
import edu.java.dto.jpa.JpaChatLinkDto;
import edu.java.repository.jpa.JpaChatLinkRepository;
import edu.java.repository.jpa.JpaChatRepository;
import edu.java.service.jpa.JpaChatLinkService;
import edu.java.service.jpa.JpaChatService;
import edu.java.service.jpa.JpaLinkService;
import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JpaChatLinkTest {
    @Autowired
    private JpaChatLinkService jpaChatLinkService;
    @Autowired
    private JpaChatLinkRepository jpaChatLinkRepository;
    @Autowired
    private JpaLinkService jpaLinkService;
    @Autowired
    private JpaChatService jpaChatService;
    @Autowired JpaChatRepository jpaChatRepository;

    private static int sizeAfterAdd;
    private static int sizeBeforeAdd;
    private static final Long CHAT_ID = 1L;
    private static final String LINK_BASE = "https://edu.tinkoff.ru";

    @Test
    @Transactional
    @Rollback
    void addTest() {
        ChatDto chatDto = new ChatDto(CHAT_ID);
        LinkDto
            linkDto = new LinkDto(
            "https://edu.tinkoff.ru",
            Timestamp.valueOf(LocalDateTime.now()),
            Timestamp.valueOf(LocalDateTime.now()),
            "testUser"
        );
        ChatLinkId chatLinkId = new ChatLinkId();
        chatLinkId.setChat(chatDto);
        chatLinkId.setLink(linkDto);
        JpaChatLinkDto jdbcChatLinkDto = new JpaChatLinkDto(chatLinkId);

        jpaChatService.register(CHAT_ID);

        sizeAfterAdd = jpaChatLinkRepository.findAll().size();

        Long linkId = jpaLinkService.add(CHAT_ID, URI.create(linkDto.getUrl()));

        jpaChatService.register(CHAT_ID);

        sizeBeforeAdd = sizeAfterAdd + 1;

        assertThat(jpaChatLinkRepository.findAll().size()).isEqualTo(sizeBeforeAdd);
    }
}
