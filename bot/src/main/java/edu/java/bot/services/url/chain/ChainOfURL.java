package edu.java.bot.services.url.chain;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.services.url.handler.URLHandler;
import edu.java.bot.services.url.strategy.DomainSetCommand;
import org.springframework.stereotype.Service;

@Service
public class ChainOfURL {
    private final URLHandler gitHub;
    private final URLHandler stackOverflow;

    public ChainOfURL(URLHandler gitHub, URLHandler stackOverflow) {
        this.gitHub = gitHub;
        this.stackOverflow = stackOverflow;
        gitHub.setNextHandler(stackOverflow);
    }

    public DomainSetCommand assemblingTheChain(Update update) {
        return gitHub.handlerURL(update);
    }
}
