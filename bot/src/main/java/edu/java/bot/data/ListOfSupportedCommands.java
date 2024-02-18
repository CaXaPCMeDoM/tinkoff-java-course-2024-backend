package edu.java.bot.data;

import com.pengrad.telegrambot.model.BotCommand;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListOfSupportedCommands {
    private List<BotCommand> commands;

    @Autowired
    public ListOfSupportedCommands(List<BotCommand> commands) {
        this.commands = commands;
    }

    public void addCommand(String commandName, String description) {
        BotCommand botCommand = new BotCommand(commandName, description);
        commands.add(botCommand);
    }

    public List<BotCommand> getCommands() {
        return commands;
    }
}
