package edu.java.scrapper.db;

import edu.java.dao.ChatDao;
import edu.java.dao.ChatLinkDao;
import edu.java.dao.LinkDao;
import edu.java.dao.dto.ChatDto;
import edu.java.dao.dto.ChatLinkDto;
import edu.java.dao.dto.LinkDto;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JdbcChatLinkTest extends IntegrationEnvironment {
    @Autowired
    private ChatLinkDao chatLinkDao;
    @Autowired
    private LinkDao linkDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ChatDao chatDao;

    private static final Long CHAT_ID = 1L;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        int sizeAfterAdd = chatLinkDao.findAll().size(); // нужна проверка при переполнении


        LinkDto linkDto = new LinkDto("https://edu.tinkoff.ru", LocalDateTime.now(), LocalDateTime.now(), "testUser");
        Long linkId = linkDao.add(linkDto);

        ChatDto chatDto = new ChatDto(CHAT_ID);
        chatDao.add(chatDto);

        ChatLinkDto chatLinkDto = new ChatLinkDto(CHAT_ID, linkId);

        chatLinkDao.add(chatLinkDto);

        int sizeBeforeAdd = sizeAfterAdd + 1; // нужна проверка при переполнении

        assertThat(chatLinkDao.findAll().size()).isEqualTo(sizeBeforeAdd);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        // Сначала добавим

        int sizeAfterAdd = chatLinkDao.findAll().size(); // нужна проверка при переполнении


        LinkDto linkDto = new LinkDto("https://edu.tinkoff.ru", LocalDateTime.now(), LocalDateTime.now(), "testUser");
        Long linkId = linkDao.add(linkDto);

        ChatDto chatDto = new ChatDto(CHAT_ID);
        chatDao.add(chatDto);

        ChatLinkDto chatLinkDto = new ChatLinkDto(CHAT_ID, linkId);
        chatLinkDao.add(chatLinkDto);

        int sizeBeforeAdd = sizeAfterAdd + 1; // нужна проверка при переполнении

        assertThat(chatLinkDao.findAll().size()).isEqualTo(sizeBeforeAdd);

        // Переходим к удалению


        int sizeAfterRemove = chatLinkDao.findAll().size(); // нужна проверка при переполнении

        chatLinkDao.remove(CHAT_ID, linkId);

        List<ChatLinkDto> chatLinkDtos = chatLinkDao.findAll();
        int sizeBeforeRemove = sizeAfterRemove - 1;

        System.out.println(chatLinkDtos);
        assertThat(chatLinkDao.findAll().size()).isEqualTo(sizeBeforeRemove);
    }
}
