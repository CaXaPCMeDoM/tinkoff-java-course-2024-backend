package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import edu.java.bot.services.command.chain.ChainOfCommand;
import edu.java.bot.services.command.handler.CommandHandler;
import edu.java.bot.services.user.UserRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChainOfCommandTest {

    // Создаем моки
    @Mock
    Update update = Mockito.mock(Update.class);
    @Mock
    User user = Mockito.mock(User.class);
    @Mock
    Message message = Mockito.mock(Message.class);
    @Mock
    TelegramBot telegramBot = Mockito.mock(TelegramBot.class);

    @Mock CommandHandler commandHandler = Mockito.mock(CommandHandler.class);

    @Mock
    UserRegistry userRegistry = Mockito.mock(UserRegistry.class);

    @InjectMocks
    ChainOfCommand chainOfCommand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        message = mock(Message.class);
        when(update.message()).thenReturn(message);

        user = mock(User.class);
        when(message.from()).thenReturn(user);
    }

    @Test
    public void testUnknownCommand() throws NoSuchFieldException, IllegalAccessException {
        telegramBot = telegramBot;

        // Настраиваем моки
        Mockito.when(user.id()).thenReturn(123L);
        Mockito.when(update.message().text()).thenReturn("/safasfs");
        Mockito.when(userRegistry.checkUserById(anyLong())).thenReturn(false);

        // Вызываем метод, который хотим протестировать
        chainOfCommand.assemblingTheChain(update);

        // Используем рефлексию для получения значения метода
        Field message = ChainOfCommand.class.getDeclaredField("messageFinal");
        message.setAccessible(true);

        // Получите значение поля 'messageListIsEmpty'
        String messageCommandDontSupported = (String) message.get(chainOfCommand);
        // Проверяем, что был вызван метод execute с нужным сообщением
        assertEquals("Команда не поддерживается или произошла ошибка ввода", messageCommandDontSupported);
    }
}
