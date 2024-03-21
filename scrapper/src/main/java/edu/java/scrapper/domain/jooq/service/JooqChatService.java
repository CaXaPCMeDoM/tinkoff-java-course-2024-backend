package edu.java.scrapper.domain.jooq.service;

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
    public void unregister(long tgChatId) {
        dslContext.delete(chat)
            .where(chat.CHAT_ID.eq(tgChatId))
            .execute();
    }
}
