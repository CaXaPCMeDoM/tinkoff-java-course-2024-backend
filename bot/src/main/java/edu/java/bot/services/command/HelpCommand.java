package edu.java.bot.services.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.data.ListOfSupportedCommands;
import edu.java.bot.services.bot.StaticBotInstance;
import edu.java.bot.services.command.handler.CommandHandler;

public class HelpCommand extends CommandHandler {
    private final String name = "/help";
    private final String messageAvailableCommands = "Список доступных команд:\n";
    private ListOfSupportedCommands listOfSupportedCommands;
    private final TelegramBot bot;

    public HelpCommand() {
        this.bot = StaticBotInstance.telegramBot;
        listOfSupportedCommands = new ListOfSupportedCommands();
    }

    @Override
    public CommandHandler handlerCommand(Update update) {
        if (update.message().text().equals(name)) {
            String chatId = update.message().chat().id().toString();
            StringBuilder stringBuilder = new StringBuilder(messageAvailableCommands);
            for (BotCommand listCommands : listOfSupportedCommands.getCommands()) {
                stringBuilder.append(listCommands.command());
                stringBuilder.append("\n");
            }
            bot.execute(new SendMessage(chatId, stringBuilder.toString()));
            return commandHandler;
        } else {
            if (commandHandler != null) {
                return commandHandler.handlerCommand(update);
            } else {
                return null;
            }
        }
    }

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Вывести окно с командами";
    }
}
