package edu.java.dao;

import edu.java.dao.dto.LinkDto;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class LinkDao {
    private static final int URL_INDEX = 1;
    private static final int LAST_CHECK_TIME_INDEX = 2;
    private static final int CREATED_AT_INDEX = 3;
    private static final int CREATED_BY_INDEX = 4;

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<LinkDto> rowMapper = (rs, rowNum) -> new LinkDto(
            rs.getLong("url_id"),
            rs.getString("url"),
            rs.getTimestamp("last_check_time").toLocalDateTime(),
            rs.getTimestamp("created_at").toLocalDateTime(),
            rs.getString("created_by")
    );

    public LinkDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public Long add(LinkDto linkDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
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
                },
                keyHolder
        );
        return (Long) Objects.requireNonNull(keyHolder.getKeys()).values().iterator().next();
    }

    @Transactional
    public void remove(Long urlId) {
        jdbcTemplate.update("DELETE FROM Link WHERE url_id = ?", urlId);
    }

    public List<LinkDto> findAll() {
        return jdbcTemplate.query("SELECT * FROM Link", rowMapper);
    }
}
