package edu.java.dao;

import edu.java.dao.dto.ChatDto;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ChatDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ChatDto> rowMapper = (rs, rowNum) -> new ChatDto(rs.getLong("chat_id"));

    public ChatDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void add(ChatDto chat) {
        jdbcTemplate.update("INSERT INTO Chat (chat_id) VALUES (?)", chat.getChatId());
    }

    @Transactional
    public void remove(Long chatId) {
        jdbcTemplate.update("DELETE FROM Chat WHERE chat_id = ?", chatId);
    }

    public List<ChatDto> findAll() {
        return jdbcTemplate.query("SELECT * FROM Chat", rowMapper);
    }
}
