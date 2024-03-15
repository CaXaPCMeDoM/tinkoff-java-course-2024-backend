package edu.java.dao;

import edu.java.dao.dto.ChatDto;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
public class ChatDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ChatDto> rowMapper = (rs, rowNum) -> new ChatDto(rs.getLong("chat_id"));

    public ChatDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void add(ChatDto chat) {
        try {
            jdbcTemplate.update("INSERT INTO Chat (chat_id) VALUES (?)", chat.getChatId());
        } catch (DuplicateKeyException e) {
            log.error("Повторяющийся ключ");
        }
    }

    @Transactional
    public Long remove(Long chatId) {
        if (jdbcTemplate.update("DELETE FROM Chat WHERE chat_id = ?", chatId) > 0) {
            return chatId;
        } else {
            throw new NoSuchElementException(String.format("Чат с ID %s не найден", chatId));
        }
    }

    public List<ChatDto> findAll() {
        return jdbcTemplate.query("SELECT * FROM Chat", rowMapper);
    }
}
