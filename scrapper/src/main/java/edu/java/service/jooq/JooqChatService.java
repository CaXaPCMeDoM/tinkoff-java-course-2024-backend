package edu.java.service.jooq;

import edu.java.scrapper.domain.jooq.tables.Chat;
import edu.java.service.ChatService;
import org.jooq.DSLContext;

public class JooqChatService implements ChatService {
    private final Chat chat = Chat.CHAT;
    private DSLContext dslContext;

    public JooqChatService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void register(long tgChatId) {
        dslContext.insertInto(chat)
            .set(chat.CHAT_ID, tgChatId);
    }

    @Override
    public Long unregister(long tgChatId) {
        return (long) dslContext.delete(chat)
            .where(chat.CHAT_ID.eq(tgChatId))
            .execute();
    }
}
