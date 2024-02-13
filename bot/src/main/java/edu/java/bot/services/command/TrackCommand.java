package edu.java.bot.services.command;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.services.command.handler.CommandHandler;
import edu.java.bot.services.url.parser.URLParser;

public class TrackCommand extends CommandHandler {
    private final String name = "/track";

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public void handlerCommand(Update update) {
        if (update.message().text().equals(name)) {
            String messageUrl = update.message().text();

            URLParser urlParser = new URLParser();
            String domain = urlParser.GetDomainName(messageUrl);
            if(!urlParser.isWasExceptionCaught()) {

            }
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
