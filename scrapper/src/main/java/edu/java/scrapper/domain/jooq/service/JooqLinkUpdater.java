package edu.java.scrapper.domain.jooq.service;

import edu.java.scrapper.domain.jooq.tables.Link;
import edu.java.service.LinkUpdater;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.jooq.DSLContext;

public class JooqLinkUpdater implements LinkUpdater {
    private final static Link LINK = Link.LINK;
    private DSLContext dslContext;

    @Override
    public void updateLinkLastCheckTime(Long linkId) {
        dslContext.update(LINK)
            .set(LINK.LAST_CHECK_TIME, LocalDateTime.now().atOffset(ZoneOffset.UTC))
            .where(LINK.URL_ID.eq(linkId))
            .execute();
    }
}
