package edu.java.repository.jooq;

import edu.java.dto.LinkDto;
import edu.java.repository.LinkRepository;
import edu.java.scrapper.domain.jooq.tables.ChatLink;
import edu.java.scrapper.domain.jooq.tables.Link;
import edu.java.scrapper.domain.jooq.tables.records.LinkRecord;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JooqLinkRepository implements LinkRepository {
    private final static Link LINK = Link.LINK;
    private final static ChatLink CHAT_LINK = ChatLink.CHAT_LINK;
    private final DSLContext dslContext;

    public JooqLinkRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Transactional
    @Override
    public void add(LinkDto linkDto) {
        dslContext.insertInto(LINK)
            .set(LINK.URL, linkDto.getUrl())
            .set(LINK.CREATED_AT, LocalDateTime.now().atOffset(ZoneOffset.UTC))
            .set(LINK.CREATED_BY, String.valueOf(linkDto.getCreatedBy()))
            .set(LINK.LAST_CHECK_TIME, LocalDateTime.now().atOffset(ZoneOffset.UTC))
            .returning()
            .fetchOne();
    }

    @Transactional
    @Override
    public LinkDto findUrlIdByLink(String link) {
        LinkRecord linkRecord = dslContext.selectFrom(LINK)
            .where(LINK.URL.eq(link))
            .fetchOne();
        if (linkRecord != null) {
            return new LinkDto(
                linkRecord.getUrlId(),
                linkRecord.getUrl(),
                Timestamp.from(linkRecord.getLastCheckTime().toInstant()),
                Timestamp.from(linkRecord.getCreatedAt().toInstant()),
                linkRecord.getCreatedBy()
            );
        }
        return null;
    }
}
