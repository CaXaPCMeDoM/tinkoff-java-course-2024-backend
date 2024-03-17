package edu.java.scrapper.domain.jooq.service;

import edu.java.scrapper.domain.jooq.tables.ChatLink;
import edu.java.service.ChatLinkService;
import java.util.List;
import org.jooq.DSLContext;

public class JooqChatLinkService implements ChatLinkService {
    private DSLContext dslContext;
    private final ChatLink chatLink = ChatLink.CHAT_LINK;

    public JooqChatLinkService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public List<Long> getChatIdsByUrlId(Long urlId) {
        return dslContext.select(chatLink.CHAT_ID)
            .from(chatLink)
            .where(chatLink.URL_ID.eq(urlId))
            .fetch()
            .into(Long.class);
    }
}
