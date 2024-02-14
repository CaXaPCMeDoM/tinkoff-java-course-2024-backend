package edu.java.bot.services.command.chain;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.data.ListOfSupportedCommands;
import edu.java.bot.services.bot.StaticBotInstance;
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

    public ChainOfCommand() {
        helpCommand = new HelpCommand();
        startCommand = new StartCommand();
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
        listOfSupportedCommands.addCommandName(startCommand.getCommandName(), startCommand.getDescription());
        listOfSupportedCommands.addCommandName(helpCommand.getCommandName(), helpCommand.getDescription());
        listOfSupportedCommands.addCommandName(listCommand.getCommandName(), listCommand.getDescription());
        listOfSupportedCommands.addCommandName(trackCommand.getCommandName(), trackCommand.getDescription());
        listOfSupportedCommands.addCommandName(untrackCommand.getCommandName(), untrackCommand.getDescription());
    }

    public void assemblingTheChain(Update update) {
        if (helpCommand.handlerCommand(update) == null) {
            StaticBotInstance.telegramBot.execute(new SendMessage(
                update.message().from().id(), "Команда не поддерживается или произошла ошибка ввода"));
        }
    }
}
