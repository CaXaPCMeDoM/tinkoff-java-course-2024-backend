package edu.java.bot.services.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.MenuButton;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.command.handler.CommandHandler;
import edu.java.bot.services.user.UserRegistry;

public class StartCommand extends CommandHandler {
    private final String name = "/start";
    private final String messageFromUserName = "Ваше имя: ";
    private final String messageFromUserThankYouForRegistering = "спасибо за регистрацию.";
    private final String messageFromUserAlreadyRegistered = "вы уже зарегистрированы, ";
    // you need to concatenate with the name
    private UserRegistry userRegistry = new UserRegistry();
    private TelegramBot bot;

    public StartCommand(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public void handlerCommand(Update update) {
        if (update.message().text().equals(name)) {
            if (userRegistry.tryAddNewUser(update.message().from().id(), update.message().from().firstName())) {
                StringBuilder stringBuilder = new StringBuilder(messageFromUserName);
                stringBuilder.append(userRegistry.getUserNameById(update.message().from().id()));
                stringBuilder.append("\n");
                stringBuilder.append(messageFromUserThankYouForRegistering);
                bot.execute(new SendMessage(
                        update.message().from().id(),
                        stringBuilder.toString()
                    )
                );
            } else {
                StringBuilder stringBuilder = new StringBuilder(messageFromUserAlreadyRegistered);
                stringBuilder.append(userRegistry.getUserNameById(update.message().from().id()));
                bot.execute(new SendMessage(
                        update.message().from().id(),
                        stringBuilder.toString()
                    )
                );
            }
            System.out.println(update.message().from().id() + " " + update.message().from().firstName());
        } else {
            if (commandHandler != null) {
                commandHandler.handlerCommand(update);
            } else {
                return;
            }
        }
    }
}
