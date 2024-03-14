package edu.java.scrapper.db;

import edu.java.dao.ChatLinkDao;
import edu.java.dao.LinkDao;
import edu.java.dao.dto.ChatLinkDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JdbcLinkTest extends IntegrationEnvironment {
    @Autowired
    private ChatLinkDao chatLinkDao;
    @Autowired
    private LinkDao linkDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Long CHAT_ID = 1L;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        Long linkId = linkDao.findAll().getFirst().getUrlId();
        ChatLinkDto chatLinkDto = new ChatLinkDto(CHAT_ID, linkId);

        chatLinkDao.add(chatLinkDto);

        assertThat(chatLinkDao.findAll().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
    }
}
