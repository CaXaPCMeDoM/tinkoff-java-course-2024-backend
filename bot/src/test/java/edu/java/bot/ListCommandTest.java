package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import edu.java.bot.data.repository.URLRepository;
import edu.java.bot.services.command.ListCommand;
import edu.java.bot.services.command.handler.CommandHandler;
import edu.java.bot.services.url.parser.GetDataFromUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListCommandTest {
    @Mock
    private Update update;
    @Mock
    private URLRepository urlRepository;
    @Mock
    private GetDataFromUpdate getDataFromUpdate;
    @Mock
    private User user;
    @Mock
    private Chat chat;
    @Mock
    private Message message;
    @Mock
    private TelegramBot bot;

    @InjectMocks
    private ListCommand listCommand;

    @BeforeEach
    public void setUp() {
        when(update.message()).thenReturn(message);

        when(message.from()).thenReturn(user);

        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(0L);
    }

    @Test
    public void testHandlerCommandWithEmptyList() throws Exception {
        // Настраиваем моки
        when(message.text()).thenReturn("/list");

        Field botField = CommandHandler.class.getDeclaredField("bot");
        botField.setAccessible(true);

        botField.set(listCommand, bot);

        // Вызываем метод, который хотим вызывать
        listCommand.handlerCommand(update);

        // Рефлексией получаем значение поля
        Field messageListIsEmptyField = ListCommand.class.getDeclaredField("message");
        messageListIsEmptyField.setAccessible(true);

        String messageListIsEmpty = (String) messageListIsEmptyField.get(listCommand);

        // проверка результатов
        assertEquals("Список пуст.", messageListIsEmpty);
    }

    @Test
    public void testHandlerCommandWithNonEmptyList() throws Exception {
        // Настраиваем моки
        when(urlRepository.getAllInString(anyString())).thenReturn("https://");
        when(message.text()).thenReturn("/list");

        Field botField = CommandHandler.class.getDeclaredField("bot");
        botField.setAccessible(true);
        botField.set(listCommand, bot);

        // Вызываем метод, который хотим вызывать
        listCommand.handlerCommand(update);

        // Рефлексией получаем значение поля
        Field messageListIsEmptyField = ListCommand.class.getDeclaredField("message");
        messageListIsEmptyField.setAccessible(true);
        String messageListIsEmpty = (String) messageListIsEmptyField.get(listCommand);

        // проверка результатов
        assertEquals("https://", messageListIsEmpty);
    }
}
