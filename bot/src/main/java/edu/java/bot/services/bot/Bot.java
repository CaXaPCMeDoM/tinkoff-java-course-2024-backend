package edu.java.bot.services.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.MenuButton;
import com.pengrad.telegrambot.model.MenuButtonCommands;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.data.ListOfSupportedCommands;
import edu.java.bot.services.command.chain.ChainOfCommand;
import edu.java.bot.web.controller.dto.LinkUpdateRequest;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Bot {
    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private ChainOfCommand chainOfCommand;

    @Autowired
    private ListOfSupportedCommands listOfSupportedCommands;

    public void createMenu() {
        /**
         *  Инициализация кнопки меню
         *  и инициализация ее полей команадмиw
         */
        List<BotCommand> commandsAndDescriptions = listOfSupportedCommands.getCommands();

        BotCommand[] botCommandArray = commandsAndDescriptions.toArray(new BotCommand[0]);
        MenuButton button = new MenuButtonCommands();
        SetMyCommands setMyCommands = new SetMyCommands(botCommandArray);

        telegramBot.execute(setMyCommands);
    }

    public void start() {
        createMenu();
        listenerBot();
    }

    public void listenerBot() {
        telegramBot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> list) {
                for (Update update : list) {
                    handlerUpdate(update);
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
    }

    public void sendAMessageAboutUpdatingLinks(Long chatId, LinkUpdateRequest linkUpdateRequest) {
        StringBuilder messageStringBuilder = new StringBuilder();
        messageStringBuilder.append("Произошло обновление ссылки: ");
        messageStringBuilder.append(linkUpdateRequest.getUrl());
        if (linkUpdateRequest.getTypeOfUpdate() != null || !linkUpdateRequest.getTypeOfUpdate().isEmpty()) {
            messageStringBuilder.append("\nТип обновления: ");
            messageStringBuilder.append(linkUpdateRequest.getTypeOfUpdate());
        }
        telegramBot.execute(new SendMessage(chatId, messageStringBuilder.toString()));
    }

    private void handlerUpdate(@NotNull Update update) {
        if (update.message() != null) {
            chainOfCommand.assemblingTheChain(update);
        }
    }
}
