package edu.java.scrapper.db.jdbc;

import edu.java.dao.jdbc.JdbcChatDao;
import edu.java.dto.ChatDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JdbcChatTest {
    @Autowired
    private JdbcChatDao jdbcChatDao;

    private static final Long CHAT_ID = -1L;

    @Test
    @Transactional
    @Rollback
    void registerChatTest() {
        int sizeAfterAdd = jdbcChatDao.findAll().size();

        ChatDto chatDto = new ChatDto(CHAT_ID);
        jdbcChatDao.add(chatDto);

        int sizeBeforeAdd = sizeAfterAdd + 1;

        assertThat(jdbcChatDao.findAll().size()).isEqualTo(sizeBeforeAdd);
    }
}
