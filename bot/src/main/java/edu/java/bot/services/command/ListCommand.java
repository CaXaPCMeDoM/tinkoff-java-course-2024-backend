package edu.java.bot.services.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.command.handler.CommandHandler;
import edu.java.bot.services.url.parser.GetDataFromUpdate;
import edu.java.bot.web.client.LinkClient;
import edu.java.bot.web.client.dto.link.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;

@Component
public class ListCommand extends CommandHandler {
    @Autowired LinkClient linkClient;

    private static final String NAME = "/list";
    private static final GetDataFromUpdate GET_DATA_FROM_UPDATE = new GetDataFromUpdate();
    private static final String MESSAGE_LIST_IS_EMPTY = "Список пуст.";
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
            List<Link> linksList = Objects.requireNonNull(linkClient.getLinksById(update.message().chat().id())
                    .block())
                .getLinks();
            StringBuilder stringBuilder = new StringBuilder();
            int i = 1;
            if (linksList == null) {
                message = MESSAGE_LIST_IS_EMPTY;
                bot.execute(new SendMessage(chatId, MESSAGE_LIST_IS_EMPTY));
            } else {
                for (Link link : linksList) {
                    stringBuilder.append((i++) + ") ");
                    stringBuilder.append(link.getUrl());
                    stringBuilder.append("\n");
                }
                message = stringBuilder.toString();
                bot.execute(new SendMessage(chatId, message));
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
