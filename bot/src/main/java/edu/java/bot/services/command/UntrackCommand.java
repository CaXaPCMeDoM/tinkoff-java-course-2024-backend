package edu.java.bot.services.command;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.services.command.handler.CommandHandler;

public class UntrackCommand extends CommandHandler {
    private final String name = "/untrack";

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public void handlerCommand(Update update) {
        if (update.message().text().equals(name)) {

        }
        else
        {
            if (commandHandler != null)
            {
                commandHandler.handlerCommand(update);
            }
            else
            {
                return;
            }
        }
    }
}
