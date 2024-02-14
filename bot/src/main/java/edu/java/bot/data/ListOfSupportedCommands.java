package edu.java.bot.data;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;
import java.util.ArrayList;
import java.util.List;

public class ListOfSupportedCommands {
    private static List<BotCommand> commands = new ArrayList<>();

    public void addCommandName(String commandName, String description) {
        BotCommand botCommand = new BotCommand(commandName, description);
        SetMyCommands setMyCommands = new SetMyCommands(botCommand);
        commands.add(botCommand);
    }

    public List<BotCommand> getCommands() {
        return commands;
    }
}
