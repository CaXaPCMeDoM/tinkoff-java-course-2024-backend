package edu.java.dao.jooq;

import edu.java.dto.ChatDto;
import edu.java.scrapper.domain.jooq.tables.ChatLink;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class JooqChatLinkDao {
    private final static ChatLink CHAT_LINK = ChatLink.CHAT_LINK;
    private DSLContext dslContext;

    public List<ChatDto> findAll() {
        return dslContext.selectFrom(CHAT_LINK)
            .fetch()
            .into(ChatDto.class);
    }
}
