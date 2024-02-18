package edu.java.bot.services.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.command.handler.CommandHandler;
import edu.java.bot.services.user.UserRegistry;
import org.springframework.stereotype.Component;

@Component
public class StartCommand extends CommandHandler {
    private static final String name = "/start";
    private static final String messageFromUserName = "Ваше имя: ";
    private static final String messageFromUserThankYouForRegistering =
            "Cпасибо за регистрацию. Бот регистрирует вас по id, поэтому "
                    + "вам не потребуется вводить ваши данные снова, если только вы не измените telegram аккаунт.";
    private static final String messageFromUserAlreadyRegistered = "вы уже зарегистрированы, ";
    private static final String messageFromUserYouNeedRegistry = "Сначала вы должны зарегистрироваться!\nКоманда: /start";
    private final UserRegistry userRegistry = new UserRegistry();

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
            handleStartCommand(update);
        } else {
            handleOtherCommands(update);
        }
        return true;
    }

    private void handleStartCommand(Update update) {
        if (userRegistry.tryAddNewUser(update.message().from().id(), update.message().from().firstName())) {
            String message = messageFromUserName + userRegistry.getUserNameById(update.message().from().id()) +
                    "\n" + messageFromUserThankYouForRegistering;
            bot.execute(new SendMessage(update.message().from().id(), message));
        } else {
            String message = messageFromUserAlreadyRegistered + userRegistry.getUserNameById(update.message().from().id());
            bot.execute(new SendMessage(update.message().from().id(), message));
        }
    }

    private void handleOtherCommands(Update update) {
        if (userRegistry.checkUserById(update.message().from().id())) {
            bot.execute(new SendMessage(update.message().from().id(), messageFromUserYouNeedRegistry));
        } else if (commandHandler != null) {
            commandHandler.handlerCommand(update);
        }
    }
}

