package edu.java.service.jooq;

import edu.java.scrapper.domain.jooq.tables.Link;
import edu.java.service.LinkUpdater;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.jooq.DSLContext;
import org.springframework.transaction.annotation.Transactional;

public class JooqLinkUpdater implements LinkUpdater {
    private final static Link LINK = Link.LINK;
    private DSLContext dslContext;

    @Transactional
    @Override
    public void updateLinkLastCheckTime(Long linkId) {
        dslContext.update(LINK)
            .set(LINK.LAST_CHECK_TIME, LocalDateTime.now().atOffset(ZoneOffset.UTC))
            .where(LINK.URL_ID.eq(linkId))
            .execute();
    }
}
