package edu.java.configuration.access.jpa;

import edu.java.repository.jpa.JpaChatLinkRepository;
import edu.java.repository.jpa.JpaChatRepository;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.service.ChatLinkService;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import edu.java.service.jpa.JpaChatLinkService;
import edu.java.service.jpa.JpaChatService;
import edu.java.service.jpa.JpaLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean
    public LinkService linkService(
        JpaLinkRepository linkRepository,
        JpaChatRepository tgChatRepository,
        JpaChatLinkRepository chatLinkRepository
    ) {
        return new JpaLinkService(linkRepository, chatLinkRepository, tgChatRepository);
    }

    @Bean
    public ChatService chatService(
        JpaChatRepository tgChatRepository
    ) {
        return new JpaChatService(tgChatRepository);
    }

    @Bean
    public ChatLinkService chatLinkService(
        JpaChatLinkRepository chatLinkRepository
    ) {
        return new JpaChatLinkService(chatLinkRepository);
    }
}
