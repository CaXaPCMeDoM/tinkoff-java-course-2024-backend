package edu.java.bot.services.url.handler;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.services.url.parser.URLParser;
import edu.java.bot.services.url.strategy.IDomainSetCommand;

public abstract class URLHandler {
    protected URLHandler urlHandler;
    protected URLParser urlParser = new URLParser();

    public URLHandler setNextHandler(URLHandler nextHandler) {
        if (urlHandler == null) {
            urlHandler = nextHandler;
        } else {
            urlHandler.setNextHandler(nextHandler);
        }

        return this;
    }

    public abstract IDomainSetCommand handlerURL(Update update);
}
