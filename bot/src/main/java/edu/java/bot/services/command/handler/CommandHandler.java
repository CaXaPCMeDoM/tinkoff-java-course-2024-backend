package edu.java.bot.services.command.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.services.url.chain.ChainOfURL;

public abstract class CommandHandler {
    public CommandHandler commandHandler;
    public static TelegramBot bot;
    protected ChainOfURL chainOfURL = new ChainOfURL();


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

    public abstract CommandHandler handlerCommand(Update update);
}
