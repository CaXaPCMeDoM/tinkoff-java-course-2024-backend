package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import edu.java.bot.services.command.ListCommand;
import edu.java.bot.services.command.handler.CommandHandler;
import edu.java.bot.services.url.parser.GetDataFromUpdate;
import edu.java.bot.web.client.ChatClient;
import edu.java.bot.web.client.LinkClient;
import edu.java.bot.web.client.dto.link.GetLinksResponse;
import edu.java.bot.web.client.dto.link.Link;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListCommandTest {
    @Mock
    private Update update;
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
    @Mock
    private ChatClient chatClient;

    @InjectMocks
    private ListCommand listCommand;
    @Mock
    private LinkClient linkClient;

    @BeforeEach
    public void setUp() {
        when(update.message()).thenReturn(message);

        when(message.from()).thenReturn(user);

        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(0L);

    }

    @Test
    public void testHandlerCommandWithEmptyList() throws Exception {
        // Создаем поддельный список ссылок
        List<Link> fakeLinks = null;

        // Создаем поддельный GetLinksResponse
        GetLinksResponse fakeResponse = new GetLinksResponse();
        fakeResponse.setLinks(fakeLinks);

        // Настраиваем мок linkClient
        when(linkClient.getLinksById(anyLong())).thenReturn(Mono.just(fakeResponse));
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
    public void testHandlerCommandWithNoneEmptyList() throws Exception {
        // Создаем поддельный список ссылок
        List<Link> fakeLinks = Arrays.asList(new Link(1L, "https://"));

        // Создаем поддельный GetLinksResponse
        GetLinksResponse fakeResponse = new GetLinksResponse();
        fakeResponse.setLinks(fakeLinks);

        // Настраиваем мок linkClient
        when(linkClient.getLinksById(anyLong())).thenReturn(Mono.just(fakeResponse));
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
        assertEquals("1) https://\n", messageListIsEmpty);
    }
}
