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
        when(getDataFromUpdate.userIdString(update)).thenReturn("123");
        when(urlRepository.getAllInString(anyString())).thenReturn(null);

        when(message.text()).thenReturn("/list");
        // Создайте мок-объект TelegramBot
        TelegramBot bot = mock(TelegramBot.class);
        // Получите поле 'bot' в классе ListCommand
        Field botField = CommandHandler.class.getDeclaredField("bot");
        botField.setAccessible(true);

        // Установите поле 'bot' в ваш мок-объект TelegramBot
        botField.set(listCommand, bot);

        // Вызовите метод, который вы тестируете
        listCommand.handlerCommand(update);

        // Получите поле 'messageListIsEmpty' в классе ListCommand
        Field messageListIsEmptyField = ListCommand.class.getDeclaredField("message");
        messageListIsEmptyField.setAccessible(true);

        // Получите значение поля 'messageListIsEmpty'
        String messageListIsEmpty = (String) messageListIsEmptyField.get(listCommand);

        // Проверьте, что значение поля 'messageListIsEmpty' соответствует ожидаемому
        assertEquals("Список пуст.", messageListIsEmpty);
    }

    @Test
    public void testHandlerCommandWithNonEmptyList() throws Exception {

        when(getDataFromUpdate.userIdString(update)).thenReturn("123");
        when(urlRepository.getAllInString(anyString())).thenReturn("https://");

        when(message.text()).thenReturn("/list");
        // Создайте мок-объект TelegramBot
        TelegramBot bot = mock(TelegramBot.class);
        // Получите поле 'bot' в классе ListCommand
        Field botField = CommandHandler.class.getDeclaredField("bot");
        botField.setAccessible(true);

        // Установите поле 'bot' в ваш мок-объект TelegramBot
        botField.set(listCommand, bot);

        // Вызовите метод, который вы тестируете
        listCommand.handlerCommand(update);

        // Получите поле 'messageListIsEmpty' в классе ListCommand
        Field messageListIsEmptyField = ListCommand.class.getDeclaredField("message");
        messageListIsEmptyField.setAccessible(true);

        // Получите значение поля 'messageListIsEmpty'
        String messageListIsEmpty = (String) messageListIsEmptyField.get(listCommand);

        // Проверьте, что значение поля 'messageListIsEmpty' соответствует ожидаемому
        assertEquals("https://", messageListIsEmpty);
    }
}
