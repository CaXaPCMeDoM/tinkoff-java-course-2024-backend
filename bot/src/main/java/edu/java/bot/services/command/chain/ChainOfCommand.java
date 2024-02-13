package edu.java.bot.services.command.chain;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.data.ListOfSupportedCommands;
import edu.java.bot.services.command.HelpCommand;
import edu.java.bot.services.command.ListCommand;
import edu.java.bot.services.command.StartCommand;
import edu.java.bot.services.command.TrackCommand;
import edu.java.bot.services.command.UntrackCommand;
import edu.java.bot.services.command.handler.CommandHandler;

public class ChainOfCommand {
    private CommandHandler helpCommand;
    private CommandHandler startCommand;
    private CommandHandler listCommand;
    private CommandHandler trackCommand;
    private CommandHandler untrackCommand;
    private ListOfSupportedCommands listOfSupportedCommands = new ListOfSupportedCommands();

    public ChainOfCommand(TelegramBot bot) {
        helpCommand = new HelpCommand(bot);
        startCommand = new StartCommand(bot);
        listCommand = new ListCommand();
        trackCommand = new TrackCommand();
        untrackCommand = new UntrackCommand();
        helpCommand.setNextHandler(
            startCommand.setNextHandler(
                listCommand.setNextHandler(
                    trackCommand.setNextHandler(untrackCommand))));
        /*  *
         * add all commands name
         */
        listOfSupportedCommands.addCommandName(startCommand.getCommandName());
        listOfSupportedCommands.addCommandName(helpCommand.getCommandName());
        listOfSupportedCommands.addCommandName(listCommand.getCommandName());
        listOfSupportedCommands.addCommandName(trackCommand.getCommandName());
        listOfSupportedCommands.addCommandName(untrackCommand.getCommandName());
    }

    public void assemblingTheChain(Update update) {
        helpCommand.handlerCommand(update);
    }
}
