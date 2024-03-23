package edu.java.configuration.access.jooq;

import edu.java.service.jooq.JooqChatLinkService;
import edu.java.service.jooq.JooqChatService;
import edu.java.service.jooq.JooqLinkService;
import edu.java.service.ChatLinkService;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfiguration {
    @Bean
    public ChatService chatServices(
        DSLContext dslContext
    ) {
        return new JooqChatService(dslContext);
    }

    @Bean
    public LinkService linkService(
        DSLContext dslContext
    ) {
        return new JooqLinkService(dslContext);
    }

    @Bean
    public ChatLinkService chatLinkService(
        DSLContext dslContext
    ) {
        return new JooqChatLinkService(dslContext);
    }
}