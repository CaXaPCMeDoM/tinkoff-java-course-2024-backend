package edu.java.dao.jdbc;

import edu.java.dto.jdbc.JdbcChatLinkDto;
import edu.java.internal.controllers.dto.ListLinksResponse;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
public class JdbcChatLinkDao {
    private static final String URL_ID_FIELD_FROM_SQL = "url_id";
    private static final String CHAT_ID_FIELD_FROM_SQL = "chat_id";

    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<JdbcChatLinkDto> ROW_MAPPER_CHAT_AND_URL_ID = (rs, rowNum) -> new JdbcChatLinkDto(
        rs.getLong(CHAT_ID_FIELD_FROM_SQL),
        rs.getLong(URL_ID_FIELD_FROM_SQL)
    );

    public JdbcChatLinkDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void add(JdbcChatLinkDto chatLink) {
        try {
            jdbcTemplate.update(
                "INSERT INTO CHAT_LINK (chat_id, url_id) VALUES (?, ?)",
                chatLink.getChatId(), chatLink.getUrlId()
            );
        } catch (DuplicateKeyException e) {
            log.warn("Повторяющийся ключ");
        }
    }

    @Transactional(readOnly = true)
    public List<Long> getChatIdsByLinkId(Long linkId) {
        return jdbcTemplate.query(
            "SELECT chat_id FROM CHAT_LINK WHERE url_id = ?",
            new Object[] {linkId},
            (rs, rowNum) -> rs.getLong(CHAT_ID_FIELD_FROM_SQL)
        );
    }

    @Transactional(readOnly = true)
    public ListLinksResponse getUrlByChatId(Long chatId) {
        String sqlGetUrlIdByChatId = "SELECT url_id FROM CHAT_LINK WHERE chat_id = ?";
        String sqlGetUrlByUrlId = "SELECT url FROM Link WHERE url_id = ?";

        RowMapper<Long> rowMapper1 = ((rs, rowNum) ->
            rs.getLong(URL_ID_FIELD_FROM_SQL)
        );
        RowMapper<String> rowMapper2 = ((rs, rowNum) ->
            rs.getString("url")
        );

        List<Long> urlIds = jdbcTemplate.query(sqlGetUrlIdByChatId, rowMapper1, chatId);
        ListLinksResponse response = new ListLinksResponse();

        for (Long urlId : urlIds) {
            String url = jdbcTemplate.queryForObject(sqlGetUrlByUrlId, rowMapper2, urlId);
            response.setLinks(Collections.singletonList(url), urlId);
        }

        return response;
    }

    @Transactional
    public void remove(Long chatId, Long urlId) {
        jdbcTemplate.update("DELETE FROM CHAT_LINK WHERE chat_id = ? AND url_id = ?", chatId, urlId);
    }

    @Transactional(readOnly = true)
    public List<JdbcChatLinkDto> findAll() {
        return jdbcTemplate.query("SELECT * FROM CHAT_LINK", ROW_MAPPER_CHAT_AND_URL_ID);
    }
}
