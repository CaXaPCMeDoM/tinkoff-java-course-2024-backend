package edu.java.bot.services.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.data.ListOfSupportedCommands;
import edu.java.bot.services.command.handler.CommandHandler;
import org.apache.kafka.common.protocol.types.Field;

public class HelpCommand extends CommandHandler {
    private final String name = "/help";
    private final String messageAvailableCommands = "Список доступных команд:\n";
    private ListOfSupportedCommands listOfSupportedCommands;
    private final TelegramBot bot;

    public HelpCommand(TelegramBot bot) {
        this.bot = bot;
        listOfSupportedCommands = new ListOfSupportedCommands();
    }

    @Override
    public void handlerCommand(Update update) {
        if (update.message().text().equals(name)) {
            String chatId = update.message().chat().id().toString();
            StringBuilder stringBuilder = new StringBuilder(messageAvailableCommands);
            for (String listCommands : listOfSupportedCommands.getCommandsName()) {
                stringBuilder.append(listCommands);
            }
            bot.execute(new SendMessage(chatId, stringBuilder.toString()));
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

    @Override
    public String getCommandName() {
        return name;
    }
}
