package edu.java.dao.jdbc;

import edu.java.dto.ChatDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class JdbcChatDao {
    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<ChatDto> ROW_MAPPER_ID = (rs, rowNum) -> new ChatDto(rs.getLong("chat_id"));

    public JdbcChatDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(ChatDto chat) {
        try {
            jdbcTemplate.update("INSERT INTO Chat (chat_id) VALUES (?)", chat.getChatId());
        } catch (DuplicateKeyException e) {
            log.error("Повторяющийся ключ");
        }
    }

    public Long remove(Long chatId) {
        return (long) jdbcTemplate.update("DELETE FROM Chat WHERE chat_id = ?", chatId);
    }

    public List<ChatDto> findAll() {
        return jdbcTemplate.query("SELECT * FROM Chat", ROW_MAPPER_ID);
    }
}
