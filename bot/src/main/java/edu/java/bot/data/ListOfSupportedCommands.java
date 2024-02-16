package edu.java.bot.data;

import com.pengrad.telegrambot.model.BotCommand;
import edu.java.bot.services.command.HelpCommand;
import edu.java.bot.services.command.ListCommand;
import edu.java.bot.services.command.StartCommand;
import edu.java.bot.services.command.TrackCommand;
import edu.java.bot.services.command.UntrackCommand;
import java.util.ArrayList;
import java.util.List;

public class ListOfSupportedCommands {
    private static List<BotCommand> commands = new ArrayList<>();

    static {
        commands.add(new BotCommand(new StartCommand().getCommandName(), new StartCommand().getDescription()));
        commands.add(new BotCommand(new HelpCommand().getCommandName(), new HelpCommand().getDescription()));
        commands.add(new BotCommand(new ListCommand().getCommandName(), new ListCommand().getDescription()));
        commands.add(new BotCommand(new TrackCommand().getCommandName(), new TrackCommand().getDescription()));
        commands.add(new BotCommand(new UntrackCommand().getCommandName(), new UntrackCommand().getDescription()));
    }

    public void addCommand(String commandName, String description) {
        BotCommand botCommand = new BotCommand(commandName, description);
        commands.add(botCommand);
    }

    public List<BotCommand> getCommands() {
        return commands;
    }
}
