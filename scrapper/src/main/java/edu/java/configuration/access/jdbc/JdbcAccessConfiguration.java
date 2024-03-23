package edu.java.configuration.access.jdbc;

import edu.java.dao.jdbc.JdbcChatDao;
import edu.java.dao.jdbc.JdbcChatLinkDao;
import edu.java.dao.jdbc.JdbcLinkDao;
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
        JdbcChatDao jdbcChatDao
    ) {
        return new JdbcChatService(jdbcChatDao);
    }

    @Bean
    public LinkService linkService(
        JdbcLinkDao jdbcLinkDao,
        JdbcChatLinkDao jdbcChatLinkDao
    ) {
        return new JdbcLinkService(jdbcLinkDao, jdbcChatLinkDao);
    }

    @Bean ChatLinkService chatLinkService(
        JdbcChatLinkDao jdbcChatLinkDao
    ) {
        return new JdbcChatLinkService(jdbcChatLinkDao);
    }
}
