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
    private String messageFinal = null;
    private final CommandHandler helpCommand;
    private final CommandHandler startCommand;
    private final CommandHandler listCommand;
    private final CommandHandler trackCommand;
    private final CommandHandler untrackCommand;
    private final ListOfSupportedCommands listOfSupportedCommands = new ListOfSupportedCommands();

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
    }

    public void assemblingTheChain(Update update) {
        if (!helpCommand.handlerCommand(update)) {
            messageFinal = "Команда не поддерживается или произошла ошибка ввода";
            StaticBotInstance.telegramBot.execute(new SendMessage(
                update.message().from().id(), messageFinal));
        }
    }
}
