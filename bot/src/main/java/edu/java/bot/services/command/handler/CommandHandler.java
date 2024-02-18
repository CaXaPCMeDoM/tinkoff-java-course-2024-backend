package edu.java.bot.services.command.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.services.url.chain.ChainOfURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class CommandHandler {
    protected CommandHandler commandHandler;

    @Autowired
    protected TelegramBot bot;

    @Autowired
    protected ChainOfURL chainOfURL;


    public CommandHandler setNextHandler(CommandHandler nextHandler) {
        if (commandHandler == null) {
            commandHandler = nextHandler;
        } else {
            commandHandler.setNextHandler(nextHandler);
        }

        return this;
    }

    public abstract String getCommandName();

    public abstract String getDescription();

    public abstract boolean handlerCommand(Update update);
}
