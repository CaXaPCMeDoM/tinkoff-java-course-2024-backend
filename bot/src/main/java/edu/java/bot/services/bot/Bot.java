package edu.java.bot.services.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.MenuButton;
import com.pengrad.telegrambot.model.MenuButtonCommands;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.data.ListOfSupportedCommands;
import edu.java.bot.services.command.chain.ChainOfCommand;
import edu.java.bot.services.command.handler.CommandHandler;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class Bot {
    private final TelegramBot telegramBot;
    private final ChainOfCommand chainOfCommand;
    private final ListOfSupportedCommands listOfSupportedCommands = new ListOfSupportedCommands();

    public Bot() {
        telegramBot = StaticBotInstance.telegramBot;
        CommandHandler.bot = telegramBot;
        chainOfCommand = new ChainOfCommand();
    }

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

    private void handlerUpdate(@NotNull Update update) {
        if (update.message() != null) {
            chainOfCommand.assemblingTheChain(update);
        }
    }
}
