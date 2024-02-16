package edu.java.bot.services.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.bot.StaticBotInstance;
import edu.java.bot.services.command.handler.CommandHandler;
import edu.java.bot.services.user.UserRegistry;

public class StartCommand extends CommandHandler {
    private final String name = "/start";
    private final String messageFromUserName = "Ваше имя: ";
    private final String messageFromUserThankYouForRegistering =
        "Cпасибо за регистрацию. Бот регистрирует вас по id, поэтому "
            + "вам не потребуется вводить ваши данные снова, если только вы не измените telegram аккаунт.";
    private final String messageFromUserAlreadyRegistered = "вы уже зарегистрированы, ";
    private final String messageFromUserYouNeedRegistry = "Сначала вы должны зарегистрироваться!\nКоманда: /start";
    // you need to concatenate with the name
    private UserRegistry userRegistry = new UserRegistry();
    private TelegramBot bot = StaticBotInstance.telegramBot;

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Зарегистрировать пользователя";
    }

    @Override
    public boolean handlerCommand(Update update) {
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

            return true;
        } else {
            if (userRegistry.checkUserById(update.message().from().id())) {
                bot.execute(new SendMessage(
                        update.message().from().id(),
                        messageFromUserYouNeedRegistry
                    )
                );
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
}
