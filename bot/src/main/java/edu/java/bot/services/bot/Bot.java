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
import java.util.ArrayList;
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

    @Autowired
    Test test;

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
        while (true) {
            try {
                List<String> bossList = new ArrayList<>();
                test.getRepositoryData("CaXaPCMeDoM", "Lab2")
                    .doOnNext(boss -> {
                        String bossString = boss.toString();
                        System.out.println("Data: " + bossString);
                        bossList.add(bossString);
                    })
                    .doOnComplete(() -> {
                        String[] bossArray = bossList.toArray(new String[0]);
                        // Теперь у вас есть массив строк bossArray, который содержит данные каждого объекта Boss
                        for (int i = 0; i < bossList.size(); i++) {
                            System.out.println(bossList.get(i));
                        }
                    })
                    .subscribe();
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("ОШибка");
            }
        }
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
