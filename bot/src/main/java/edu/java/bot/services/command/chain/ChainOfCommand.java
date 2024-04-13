package edu.java.bot.services.command.chain;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.data.ListOfSupportedCommands;
import edu.java.bot.services.command.handler.CommandHandler;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.springframework.stereotype.Service;

@Service
public class ChainOfCommand {
    private String messageFinal = null;

    private final CommandHandler helpCommand;
    private final CommandHandler startCommand;
    private final CommandHandler listCommand;
    private final CommandHandler trackCommand;
    private final CommandHandler untrackCommand;
    private final ListOfSupportedCommands listOfSupportedCommands;
    private final TelegramBot telegramBot;
    private Counter messagesCounter = Counter
        .builder("messages_processed")
        .description("Number of processed messages")
        .register(Metrics.globalRegistry);

    public ChainOfCommand(
        CommandHandler helpCommand,
        CommandHandler startCommand,
        CommandHandler listCommand,
        CommandHandler trackCommand,
        CommandHandler untrackCommand,
        ListOfSupportedCommands listOfSupportedCommands,
        TelegramBot telegramBot
    ) {
        helpCommand.setNextHandler(
            startCommand.setNextHandler(
                listCommand.setNextHandler(
                    trackCommand.setNextHandler(untrackCommand))));
        this.helpCommand = helpCommand;
        this.startCommand = startCommand;
        this.listCommand = listCommand;
        this.trackCommand = trackCommand;
        this.untrackCommand = untrackCommand;
        this.listOfSupportedCommands = listOfSupportedCommands;
        this.telegramBot = telegramBot;
    }

    public void assemblingTheChain(Update update) {
        if (!helpCommand.handlerCommand(update)) {
            messageFinal = "Команда не поддерживается или произошла ошибка ввода";
            telegramBot.execute(new SendMessage(
                update.message().from().id(), messageFinal));
        }
        messagesCounter.increment();
    }
}
