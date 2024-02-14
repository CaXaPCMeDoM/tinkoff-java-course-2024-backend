package edu.java.bot.services.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.data.repository.URLRepository;
import edu.java.bot.services.command.handler.CommandHandler;
import edu.java.bot.services.url.parser.GetDataFromUpdate;

public class ListCommand extends CommandHandler {
    private final String name = "/list";
    private GetDataFromUpdate getDataFromUpdate = new GetDataFromUpdate();
    private final String messageListIsEmpty = "Список пуст.";

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Показать список отслеживаемых ссылок";
    }

    @Override
    public boolean handlerCommand(Update update) {
        String userId = getDataFromUpdate.userIdString(update);
        String chatId = update.message().chat().id().toString();
        if (update.message().text().equals(name)) {
            URLRepository urlRepository = new URLRepository();

            String message = urlRepository.getAllInString(userId);

            if (message != null) {
                bot.execute(new SendMessage(chatId, message));
            } else {
                bot.execute(new SendMessage(chatId, messageListIsEmpty));
            }

            return true;
        } else {
            if (commandHandler != null) {
                return commandHandler.handlerCommand(update);
            } else {
                return false;
            }
        }
    }
}
