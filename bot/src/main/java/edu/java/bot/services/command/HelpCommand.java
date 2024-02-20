package edu.java.bot.services.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.data.ListOfSupportedCommands;
import edu.java.bot.services.command.handler.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand extends CommandHandler {
    private static final String NAME = "/help";
    private static final String MESSAGE_AVAILABLE_COMMANDS = "Список доступных команд:\n";

    @Autowired
    private ListOfSupportedCommands listOfSupportedCommands;

    @Override
    public boolean handlerCommand(Update update) {
        if (NAME.equals(update.message().text())) {
            String chatId = update.message().chat().id().toString();
            StringBuilder stringBuilder = new StringBuilder(MESSAGE_AVAILABLE_COMMANDS);
            for (BotCommand listCommands : listOfSupportedCommands.getCommands()) {
                stringBuilder.append(listCommands.command());
                stringBuilder.append("\n");
            }
            bot.execute(new SendMessage(chatId, stringBuilder.toString()));
            return true;
        } else {
            if (commandHandler == null) {
                return false;
            } else {
                return commandHandler.handlerCommand(update);
            }
        }
    }

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return "Вывести окно с командами";
    }
}
