package edu.java.bot.services.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.command.handler.CommandHandler;
import edu.java.bot.services.url.parser.GetDataFromUpdate;
import edu.java.bot.services.url.parser.URLParser;
import edu.java.bot.services.url.strategy.IDomainSetCommand;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TrackCommand extends CommandHandler {
    private final String name = "/track";
    private final String messageEnd = "URL отслеживается";
    private IDomainSetCommand domainCommand = null;
    private GetDataFromUpdate getDataFromUpdate = new GetDataFromUpdate();
    private Map<String, Boolean> userState = new ConcurrentHashMap<>();

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Начать отслеживание ссылки";
    }

    @Override
    public boolean handlerCommand(Update update) {
        String userId = getDataFromUpdate.userIdString(update);
        String messageText = update.message().text();

        if (messageText.equals(name)) {
            userState.put(userId, true);
            String chatId = update.message().chat().id().toString();
            bot.execute(new SendMessage(chatId, "Теперь отправьте ссылку"));
        } else if (userState.getOrDefault(userId, false)) {
            URLParser urlParser = new URLParser();

            String chatId = update.message().chat().id().toString();

            if (!urlParser.isWasExceptionCaught()) {
                domainCommand = chainOfURL.assemblingTheChain(update);
                if (domainCommand == null) {
                    bot.execute(new SendMessage(chatId, "Данный домен не поддерживается, либо ссылка некорректна!"));
                    return true;
                } else {
                    domainCommand.startTracking(userId, messageText);
                }
            }

            userState.remove(userId);
            bot.execute(new SendMessage(chatId, messageEnd));
        } else {
            if (commandHandler != null) {
                return commandHandler.handlerCommand(update);
            } else {
                return false;
            }
        }
        return true;
    }
}

