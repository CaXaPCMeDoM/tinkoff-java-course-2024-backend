package edu.java.scrapper.domain.jooq.service;

import edu.java.scrapper.domain.jooq.tables.Link;
import edu.java.service.LinkUpdater;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.jooq.DSLContext;

public class JooqLinkUpdater implements LinkUpdater {
    private DSLContext dslContext;
    private final static Link LINK = Link.LINK;

    /*
    @Transactional
    public void updateLastCheckTimeByLink(Long linkId) {
        String sqlQuery = "UPDATE Link SET last_check_time = ? where url_id = ?";
        jdbcTemplate.update(sqlQuery, Timestamp.valueOf(LocalDateTime.now()), linkId);
    }
     */
    @Override
    public void updateLinkLastCheckTime(Long linkId) {
        dslContext.update(LINK)
            .set(LINK.LAST_CHECK_TIME, LocalDateTime.now().atOffset(ZoneOffset.UTC))
            .where(LINK.URL_ID.eq(linkId))
            .execute();
    }
}
