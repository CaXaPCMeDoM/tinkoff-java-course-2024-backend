package edu.java.bot.services.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.data.ListOfSupportedCommands;
import edu.java.bot.services.command.chain.ChainOfCommand;
import java.util.List;

public class Bot {
    private final TelegramBot telegramBot;
    private final ChainOfCommand chainOfCommand;

    public Bot(String token) {
        telegramBot = new TelegramBot(token);
        chainOfCommand = new ChainOfCommand(telegramBot);
    }

    public void initBot() {
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

    private void handlerUpdate(Update update) {
        if (update.message() != null) {
            chainOfCommand.assemblingTheChain(update);
        }
    }
}
