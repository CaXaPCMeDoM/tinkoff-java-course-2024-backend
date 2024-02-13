package edu.java.bot.services.command;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.services.command.handler.CommandHandler;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.web.util.UriBuilder;
import java.net.URI;
import java.net.URISyntaxException;

public class ListCommand extends CommandHandler {
    private final String name = "/list";

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public void handlerCommand(Update update) {
        if (update.message().text().equals(name)) {


        } else {
            if (commandHandler != null) {
                commandHandler.handlerCommand(update);
            } else {
                return;
            }
        }
    }
}
