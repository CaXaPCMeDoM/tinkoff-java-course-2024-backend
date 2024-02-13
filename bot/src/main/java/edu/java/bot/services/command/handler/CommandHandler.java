package edu.java.bot.services.command.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;

public abstract class CommandHandler {
    public CommandHandler commandHandler;

    public CommandHandler setNextHandler(CommandHandler nextHandler) {
        if (commandHandler == null) {
            commandHandler = nextHandler;
        } else {
            commandHandler.setNextHandler(nextHandler);
        }

        return this;
    }

    public abstract String getCommandName();

    public abstract void handlerCommand(Update update);
}
