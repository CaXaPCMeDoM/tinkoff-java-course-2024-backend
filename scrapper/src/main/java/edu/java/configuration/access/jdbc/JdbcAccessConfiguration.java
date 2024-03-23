package edu.java.configuration.access.jdbc;

import edu.java.dao.jdbc.ChatDao;
import edu.java.dao.jdbc.ChatLinkDao;
import edu.java.dao.jdbc.LinkDao;
import edu.java.service.ChatLinkService;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import edu.java.service.jdbc.JdbcChatLinkService;
import edu.java.service.jdbc.JdbcChatService;
import edu.java.service.jdbc.JdbcLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public ChatService chatServices(
        ChatDao chatDao
    ) {
        return new JdbcChatService(chatDao);
    }

    @Bean
    public LinkService linkService(
        LinkDao linkDao,
        ChatLinkDao chatLinkDao
    ) {
        return new JdbcLinkService(linkDao, chatLinkDao);
    }

    @Bean ChatLinkService chatLinkService(
        ChatLinkDao chatLinkDao
    ) {
        return new JdbcChatLinkService(chatLinkDao);
    }
}
