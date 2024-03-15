package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import edu.java.bot.services.command.chain.ChainOfCommand;
import edu.java.bot.services.command.handler.CommandHandler;
import java.lang.reflect.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChainOfCommandTest {

    // Создаем моки
    @Mock
    Update update;
    @Mock
    User user;
    @Mock
    Message message;
    @Mock
    TelegramBot telegramBot;

    @Mock CommandHandler commandHandler;

    @InjectMocks
    ChainOfCommand chainOfCommand;

    @BeforeEach
    public void setUp() {
        when(update.message()).thenReturn(message);
        when(message.from()).thenReturn(user);
    }

    @Test
    public void testUnknownCommand() throws NoSuchFieldException, IllegalAccessException {
        // Настраиваем моки
        when(user.id()).thenReturn(123L);

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
