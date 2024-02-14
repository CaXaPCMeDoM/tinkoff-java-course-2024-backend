package edu.java.bot.services.url;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.services.url.handler.URLHandler;
import edu.java.bot.services.url.strategy.IDomainSetCommand;
import edu.java.bot.services.url.strategy.resources.support.stackoverflow.StackOverflowCommand;

public class StackOverflow extends URLHandler {
    private final String nameDomain = "stackoverflow.com";

    @Override
    public IDomainSetCommand handlerURL(Update update) {
        String domain = urlParser.getDomainName(update.message().text());
        if (nameDomain.equals(domain)) {
            return new StackOverflowCommand();
        } else {
            if (urlHandler != null) {
                return urlHandler.handlerURL(update);
            } else {
                return null;
            }
        }
    }
}
