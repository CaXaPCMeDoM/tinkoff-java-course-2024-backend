package edu.java.bot.services.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.data.repository.URLRepository;
import edu.java.bot.services.command.handler.CommandHandler;
import edu.java.bot.services.url.parser.GetDataFromUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListCommand extends CommandHandler {
    private static final String NAME = "/list";
    private static final GetDataFromUpdate GET_DATA_FROM_UPDATE = new GetDataFromUpdate();
    private static final String MESSAGE_LIST_IS_EMPTY = "Список пуст.";
    @Autowired
    private URLRepository urlRepository;
    private String message = null;

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return "Показать список отслеживаемых ссылок";
    }

    @Override
    public boolean handlerCommand(Update update) {
        String userId = GET_DATA_FROM_UPDATE.userIdString(update);
        String chatId = update.message().chat().id().toString();
        if (update.message().text().equals(NAME)) {

            message = urlRepository.getAllInString(userId);

            if (message != null) {
                bot.execute(new SendMessage(chatId, message));
            } else {
                message = MESSAGE_LIST_IS_EMPTY;
                bot.execute(new SendMessage(chatId, MESSAGE_LIST_IS_EMPTY));
            }

            return true;
        } else {
            if (commandHandler == null) {
                return false;
            } else {
                return commandHandler.handlerCommand(update);
            }
        }
    }
}
