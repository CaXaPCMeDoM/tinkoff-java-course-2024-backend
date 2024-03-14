package edu.java.dao;

import edu.java.dao.dto.LinkDto;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LinkDao {
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
    public void add(LinkDto linkDto) {
        jdbcTemplate.update(
            "INSERT INTO link (url, last_check_time, created_at, created_by) VALUES (?, ?, ?, ?)",
            linkDto.getUrl(), linkDto.getLastCheckTime(), linkDto.getCreatedAt(), linkDto.getCreatedBy()
        );
    }

    @Transactional
    public void remove(Long urlId) {
        jdbcTemplate.update("DELETE FROM Link WHERE url_id = ?", urlId);
    }

    public List<LinkDto> findAll() {
        return jdbcTemplate.query("SELECT * FROM Link", rowMapper);
    }
}
