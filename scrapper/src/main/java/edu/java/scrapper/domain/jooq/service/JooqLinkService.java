package edu.java.scrapper.domain.jooq.service;

import edu.java.dao.dto.LinkDto;
import edu.java.internal.controllers.dto.ListLinksResponse;
import edu.java.scrapper.domain.jooq.tables.ChatLink;
import edu.java.scrapper.domain.jooq.tables.Link;
import edu.java.scrapper.domain.jooq.tables.records.ChatLinkRecord;
import edu.java.scrapper.domain.jooq.tables.records.LinkRecord;
import edu.java.service.LinkService;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

@Service
public class JooqLinkService implements LinkService {
    private DSLContext dslContext;

    @Override
    public Long add(long tgChatId, URI url) {
        Link link = Link.LINK;
        OffsetDateTime dateTime = LocalDateTime.now().atOffset(ZoneOffset.UTC);
        LinkRecord linkRecord = dslContext.insertInto(link)
            .set(link.URL, url.toString())
            .set(link.CREATED_AT, dateTime)
            .set(link.CREATED_BY, String.valueOf(tgChatId))
            .set(link.LAST_CHECK_TIME, dateTime)
            .returning()
            .fetchOne();

        if (linkRecord != null) {
            ChatLinkRecord chatLinkRecord = dslContext.insertInto(ChatLink.CHAT_LINK)
                .set(ChatLink.CHAT_LINK.CHAT_ID, tgChatId)
                .set(ChatLink.CHAT_LINK.URL_ID, linkRecord.getUrlId())
                .returning()
                .fetchOne();
            return linkRecord.getUrlId();
        }
        return null;
    }

    @Override
    public void remove(long tgChatId, URI url) {
    }

    @Override
    public ListLinksResponse listAllByChatId(long tgChatId) {
        return null;
    }

    @Override
    public List<LinkDto> listAll() {
        return null;
    }
}
