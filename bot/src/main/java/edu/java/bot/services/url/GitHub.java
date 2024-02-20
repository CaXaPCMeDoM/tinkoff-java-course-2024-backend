package edu.java.bot.services.url;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.services.url.handler.URLHandler;
import edu.java.bot.services.url.strategy.DomainSetCommand;
import edu.java.bot.services.url.strategy.resources.support.github.GitHubCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GitHub extends URLHandler {
    private static final String NAME_DOMAIN = "github.com";
    @Autowired
    private GitHubCommand gitHubCommand;

    @Override
    public DomainSetCommand handlerURL(Update update) {
        String domain = urlParser.getDomainName(update.message().text());
        if (NAME_DOMAIN.equals(domain)) {
            return gitHubCommand;
        } else {
            if (urlHandler != null) {
                return urlHandler.handlerURL(update);
            } else {
                return null;
            }
        }
    }
}
