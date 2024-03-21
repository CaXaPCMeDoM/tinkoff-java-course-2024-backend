package edu.java.scrapper.domain.jooq.service;

import edu.java.dto.jdbc.JdbcLinkDto;
import edu.java.internal.controllers.dto.ListLinksResponse;
import edu.java.scrapper.domain.jooq.tables.ChatLink;
import edu.java.scrapper.domain.jooq.tables.Link;
import edu.java.scrapper.domain.jooq.tables.records.ChatLinkRecord;
import edu.java.scrapper.domain.jooq.tables.records.LinkRecord;
import edu.java.service.LinkService;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JooqLinkService implements LinkService {
    private final static Link LINK = Link.LINK;
    private final static ChatLink CHAT_LINK = ChatLink.CHAT_LINK;
    private DSLContext dslContext;

    public JooqLinkService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    @Transactional
    public Long add(long tgChatId, URI url) {
        LinkRecord linkRecord = dslContext.insertInto(LINK)
            .set(LINK.URL, url.toString())
            .set(LINK.CREATED_AT, LocalDateTime.now().atOffset(ZoneOffset.UTC))
            .set(LINK.CREATED_BY, String.valueOf(tgChatId))
            .set(LINK.LAST_CHECK_TIME, LocalDateTime.now().atOffset(ZoneOffset.UTC))
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
    @Transactional
    public void remove(long tgChatId, URI url) {
        Long linkId;
        @Nullable Record1<Long> linkRecord = dslContext.select(LINK.URL_ID)
            .from(LINK)
            .where(LINK.URL.eq(url.toString()))
            .fetchOne();
        if (linkRecord != null) {
            linkId = linkRecord.component1();
            dslContext.delete(CHAT_LINK)
                .where(CHAT_LINK.URL_ID.eq(linkId))
                .execute();
        }
    }

    @Override
    public ListLinksResponse listAllByChatId(long tgChatId) {
        ListLinksResponse response = new ListLinksResponse();
        List<String> links = new ArrayList<>();

        Result<Record1<Long>> result = dslContext.select(CHAT_LINK.URL_ID)
            .from(CHAT_LINK)
            .where(CHAT_LINK.CHAT_ID.eq(tgChatId))
            .fetch();

        for (Record1<Long> records : result) {
            Long urlId = records.value1();
            String url = Objects.requireNonNull(dslContext.select(LINK.URL)
                    .from(LINK)
                    .where(LINK.URL_ID.eq(urlId))
                    .fetchOne())
                .value1();
            links.add(url);
        }

        response.setLinks(links, tgChatId);
        return response;
    }

    @Override
    public List<JdbcLinkDto> listAll() {
        return dslContext.selectFrom(LINK)
            .fetch()
            .into(JdbcLinkDto.class);
    }
}
