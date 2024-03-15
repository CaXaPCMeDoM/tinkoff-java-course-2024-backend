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

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<LinkDto> rowMapper = (rs, rowNum) -> new LinkDto(
        rs.getString("url"),
        rs.getTimestamp("last_check_time").toLocalDateTime(),
        rs.getTimestamp("created_at").toLocalDateTime(),
        rs.getString("created_by")
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
                    rs.getString("url"),
                    rs.getTimestamp("last_check_time").toLocalDateTime(),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getString("created_by")
                );
                linkDtoFromFindAll.setLinkId(rs.getLong("url_id"));
                return linkDtoFromFindAll;
            };
            return jdbcTemplate.query("SELECT * FROM Link", rowMapper2);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
