package edu.java.bot.services.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.command.handler.CommandHandler;
import edu.java.bot.services.user.UserRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartCommand extends CommandHandler {
    private static final String NAME = "/start";
    private static final String MESSAGE_FROM_USER_NAME = "Ваше имя: ";
    private static final String MESSAGE_FROM_USER_THANK_YOU_FOR_REGISTERING =
        "Cпасибо за регистрацию. Бот регистрирует вас по id, поэтому "
            + "вам не потребуется вводить ваши данные снова, если только вы не измените telegram аккаунт.";
    private static final String MESSAGE_FROM_USER_ALREADY_REGISTERED = "вы уже зарегистрированы, ";
    private static final String MESSAGE_FROM_USER_YOU_NEED_REGISTRY = "Сначала вы должны зарегистрироваться!"
        + "\nКоманда: /start";

    @Autowired
    private UserRegistry userRegistry;

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return "Зарегистрировать пользователя";
    }

    @Override
    public boolean handlerCommand(Update update) {
        if (update.message().text().equals(NAME)) {
            handleStartCommand(update);
        } else {
            handleOtherCommands(update);
        }
        return true;
    }

    private void handleStartCommand(Update update) {
        if (userRegistry.tryAddNewUser(update.message().from().id(), update.message().from().firstName())) {
            String message = MESSAGE_FROM_USER_NAME + userRegistry.getUserNameById(update.message().from().id())
                + "\n" + MESSAGE_FROM_USER_THANK_YOU_FOR_REGISTERING;
            bot.execute(new SendMessage(update.message().from().id(), message));
        } else {
            String message =
                MESSAGE_FROM_USER_ALREADY_REGISTERED
                    + userRegistry.getUserNameById(update.message().from().id());
            bot.execute(new SendMessage(update.message().from().id(), message));
        }
    }

    private void handleOtherCommands(Update update) {
        if (userRegistry.checkUserById(update.message().from().id())) {
            bot.execute(new SendMessage(update.message().from().id(), MESSAGE_FROM_USER_YOU_NEED_REGISTRY));
        } else if (commandHandler != null) {
            commandHandler.handlerCommand(update);
        }
    }
}

