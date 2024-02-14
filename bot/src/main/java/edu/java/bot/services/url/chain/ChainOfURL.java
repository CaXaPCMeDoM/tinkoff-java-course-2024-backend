package edu.java.bot.services.url.chain;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.data.ListOfSupportedCommands;
import edu.java.bot.services.url.GitHub;
import edu.java.bot.services.url.StackOverflow;
import edu.java.bot.services.url.handler.URLHandler;
import edu.java.bot.services.url.strategy.IDomainSetCommand;

public class ChainOfURL {
    private URLHandler gitHub = new GitHub();
    private URLHandler stackOverflow = new StackOverflow();
    private ListOfSupportedCommands listOfSupportedCommands = new ListOfSupportedCommands();

    public ChainOfURL() {
        gitHub.setNextHandler(stackOverflow);
    }

    public IDomainSetCommand assemblingTheChain(Update update) {
        return gitHub.handlerURL(update);
    }
}
