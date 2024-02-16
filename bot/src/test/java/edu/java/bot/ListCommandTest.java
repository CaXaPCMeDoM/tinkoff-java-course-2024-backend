package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.utility.BotUtils;
import edu.java.bot.data.repository.URLRepository;
import edu.java.bot.services.command.ListCommand;
import edu.java.bot.services.command.handler.CommandHandler;
import edu.java.bot.services.url.parser.GetDataFromUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import static edu.java.bot.services.command.handler.CommandHandler.bot;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @InjectMocks
    private ListCommand listCommand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        message = mock(Message.class);
        when(update.message()).thenReturn(message);

        user = mock(User.class);
        when(message.from()).thenReturn(user);

        chat = mock(Chat.class);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(0L);
    }

    @Test
    public void testHandlerCommandWithEmptyList() throws Exception {
        // Настраиваем моки
        when(getDataFromUpdate.userIdString(update)).thenReturn("123");
        when(urlRepository.getAllInString(anyString())).thenReturn(null);
        when(message.text()).thenReturn("/list");

        TelegramBot bot = mock(TelegramBot.class);
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
        when(getDataFromUpdate.userIdString(update)).thenReturn("123");
        when(urlRepository.getAllInString(anyString())).thenReturn("https://");
        when(message.text()).thenReturn("/list");

        TelegramBot bot = mock(TelegramBot.class);
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
