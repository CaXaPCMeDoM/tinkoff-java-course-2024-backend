package edu.java.service.jooq;

import edu.java.scrapper.domain.jooq.tables.ChatLink;
import edu.java.service.ChatLinkService;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.transaction.annotation.Transactional;

public class JooqChatLinkService implements ChatLinkService {
    private final ChatLink chatLink = ChatLink.CHAT_LINK;
    private DSLContext dslContext;

    public JooqChatLinkService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Transactional
    @Override
    public List<Long> getChatIdsByUrlId(Long urlId) {
        return dslContext.select(chatLink.CHAT_ID)
            .from(chatLink)
            .where(chatLink.URL_ID.eq(urlId))
            .fetch()
            .into(Long.class);
    }
}
