package edu.java.dao;

import edu.java.dao.dto.ChatLinkDto;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ChatLinkDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ChatLinkDto> rowMapper = (rs, rowNum) -> new ChatLinkDto(
        rs.getLong("chat_id"),
        rs.getLong("url_id")
    );

    public ChatLinkDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void add(ChatLinkDto chatLink) {
        jdbcTemplate.update(
            "INSERT INTO CHAT_LINK (chat_id, url_id) VALUES (?, ?)",
            chatLink.getChatId(), chatLink.getUrlId()
        );
    }

    @Transactional
    public void remove(Long chatId, Long urlId) {
        jdbcTemplate.update("DELETE FROM CHAT_LINK WHERE chat_id = ? AND url_id = ?", chatId, urlId);
    }

    public List<ChatLinkDto> findAll() {
        return jdbcTemplate.query("SELECT * FROM CHAT_LINK", rowMapper);
    }
}
