package edu.java.scrapper.db.jdbc;

import edu.java.dao.jdbc.ChatDao;
import edu.java.dao.jdbc.ChatLinkDao;
import edu.java.dao.jdbc.LinkDao;
import edu.java.dto.ChatDto;
import edu.java.dto.jdbc.JdbcChatLinkDto;
import edu.java.dto.LinkDto;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import edu.java.scrapper.db.IntegrationEnvironment;
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

        LinkDto
            linkDto = new LinkDto("https://edu.tinkoff.ru",
            Timestamp.valueOf(LocalDateTime.now()),
            Timestamp.valueOf(LocalDateTime.now()),
            "testUser"
        );
        linkDao.add(linkDto);
        Long linkId = linkDao.getIdByUrl(linkDto.getUrl());

        ChatDto chatDto = new ChatDto(CHAT_ID);
        chatDao.add(chatDto);

        JdbcChatLinkDto jdbcChatLinkDto = new JdbcChatLinkDto(CHAT_ID, linkId);

        chatLinkDao.add(jdbcChatLinkDto);

        int sizeBeforeAdd = sizeAfterAdd + 1; // нужна проверка при переполнении

        assertThat(chatLinkDao.findAll().size()).isEqualTo(sizeBeforeAdd);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        // Сначала добавим

        int sizeAfterAdd = chatLinkDao.findAll().size(); // нужна проверка при переполнении

        LinkDto
            linkDto = new LinkDto("https://edu.tinkoff.ru", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), "testUser");
        linkDao.add(linkDto);
        Long linkId = linkDao.getIdByUrl(linkDto.getUrl());

        ChatDto chatDto = new ChatDto(CHAT_ID);
        chatDao.add(chatDto);

        JdbcChatLinkDto jdbcChatLinkDto = new JdbcChatLinkDto(CHAT_ID, linkId);
        chatLinkDao.add(jdbcChatLinkDto);

        int sizeBeforeAdd = sizeAfterAdd + 1; // нужна проверка при переполнении

        assertThat(chatLinkDao.findAll().size()).isEqualTo(sizeBeforeAdd);

        // Переходим к удалению

        int sizeAfterRemove = chatLinkDao.findAll().size(); // нужна проверка при переполнении

        chatLinkDao.remove(CHAT_ID, linkId);

        int sizeBeforeRemove = sizeAfterRemove - 1;

        assertThat(chatLinkDao.findAll().size()).isEqualTo(sizeBeforeRemove);
    }
}
