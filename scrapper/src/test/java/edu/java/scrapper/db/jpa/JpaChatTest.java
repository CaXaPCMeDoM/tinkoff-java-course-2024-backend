package edu.java.scrapper.db.jpa;

import edu.java.repository.jpa.JpaChatRepository;
import edu.java.service.jpa.JpaChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JpaChatTest {
    @Autowired
    private JpaChatService jpaChatService;
    @Autowired
    private JpaChatRepository chatRepository;

    private static final Long CHAT_ID = -1L;

    @Test
    @Transactional
    @Rollback
    void registerChatTest() {
        int sizeAfterAdd = chatRepository.findAll().size(); // нужна проверка при переполнении

        jpaChatService.register(CHAT_ID);

        int sizeBeforeAdd = sizeAfterAdd + 1; // нужна проверка при переполнении

        assertThat(chatRepository.findAll().size()).isEqualTo(sizeBeforeAdd);
    }
}
