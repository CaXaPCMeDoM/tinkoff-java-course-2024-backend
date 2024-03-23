package edu.java.scrapper.db.jdbc;

import edu.java.dao.jdbc.ChatDao;
import edu.java.dto.ChatDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JdbcChatTest {
    @Autowired
    private ChatDao chatDao;

    private static final Long CHAT_ID = -1L;

    @Test
    @Transactional
    @Rollback
    void registerChatTest() {
        int sizeAfterAdd = chatDao.findAll().size();

        ChatDto chatDto = new ChatDto(CHAT_ID);
        chatDao.add(chatDto);

        int sizeBeforeAdd = sizeAfterAdd + 1;

        assertThat(chatDao.findAll().size()).isEqualTo(sizeBeforeAdd);
    }
}
