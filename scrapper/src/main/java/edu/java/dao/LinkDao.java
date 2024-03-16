package edu.java.dao;

import edu.java.dao.dto.LinkDto;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
public class LinkDao {
    private static final int URL_INDEX = 1;
    private static final int LAST_CHECK_TIME_INDEX = 2;
    private static final int CREATED_AT_INDEX = 3;
    private static final int CREATED_BY_INDEX = 4;
    private static final String URL_FIELD_FROM_SQL = "url";
    private static final String URL_ID_FIELD_FROM_SQL = "url_id";
    private static final String LAST_CHECK_TIME_FIELD_FROM_SQL = "last_check_time";
    private static final String CREATED_AT_FIELD_FROM_SQL = "created_at";
    private static final String CREATED_BY_FIELD_FROM_SQL = "created_by";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<LinkDto> rowMapper = (rs, rowNum) -> new LinkDto(
        rs.getString(URL_FIELD_FROM_SQL),
        rs.getTimestamp(LAST_CHECK_TIME_FIELD_FROM_SQL).toLocalDateTime(),
        rs.getTimestamp(CREATED_AT_FIELD_FROM_SQL).toLocalDateTime(),
        rs.getString(CREATED_BY_FIELD_FROM_SQL)
    );

    public LinkDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    public Long getIdByUrl(String url) {
        String sql = "SELECT url_id FROM Link WHERE url = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[] {url}, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Transactional
    public void updateLastCheckTimeByLink(Long linkId) {
        String sqlQuery = "UPDATE Link SET last_check_time = ? where url_id = ?";
        jdbcTemplate.update(sqlQuery, Timestamp.valueOf(LocalDateTime.now()), linkId);
    }

    @Transactional
    public void add(LinkDto linkDto) {
        try {
            jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO link (url, last_check_time, created_at, created_by) VALUES (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                    );
                    ps.setString(URL_INDEX, linkDto.getUrl());
                    ps.setTimestamp(LAST_CHECK_TIME_INDEX, Timestamp.valueOf(linkDto.getLastCheckTime()));
                    ps.setTimestamp(CREATED_AT_INDEX, Timestamp.valueOf(linkDto.getCreatedAt()));
                    ps.setString(CREATED_BY_INDEX, linkDto.getCreatedBy());
                    return ps;
                }
            );
        } catch (DuplicateKeyException e) {
            log.warn("Повторяющийся ключ ");
        }
    }

    @Transactional
    public void remove(Long urlId) {
        jdbcTemplate.update("DELETE FROM Link WHERE url_id = ?", urlId);
    }

    @Transactional(readOnly = true)
    public List<LinkDto> findAll() {
        try {
            RowMapper<LinkDto> rowMapper2 = (rs, rowNum) -> {
                LinkDto linkDtoFromFindAll = new LinkDto(
                    rs.getString(URL_FIELD_FROM_SQL),
                    rs.getTimestamp(LAST_CHECK_TIME_FIELD_FROM_SQL).toLocalDateTime(),
                    rs.getTimestamp(CREATED_AT_FIELD_FROM_SQL).toLocalDateTime(),
                    rs.getString(CREATED_BY_FIELD_FROM_SQL)
                );
                linkDtoFromFindAll.setLinkId(rs.getLong(URL_ID_FIELD_FROM_SQL));
                return linkDtoFromFindAll;
            };
            return jdbcTemplate.query(
                "SELECT * FROM Link WHERE last_check_time < NOW() - INTERVAL '1 minutes'",
                rowMapper2
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
