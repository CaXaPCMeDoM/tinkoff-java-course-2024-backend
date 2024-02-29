package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import edu.java.bot.services.command.StartCommand;
import edu.java.bot.services.user.UserRegistry;
import edu.java.bot.web.client.ChatClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StartCommandTest {

    @Mock
    private UserRegistry userRegistry;

    @Mock
    private TelegramBot bot;

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Mock
    private User user;

    @Mock
    private Chat chat;

    @Mock
    private ChatClient chatClient;

    @InjectMocks
    private StartCommand startCommand;

    @Test
    public void testCorrectRegistrationIsTakingPlace() {
        // Настраиваем фиктивные данные
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/start");
        when(message.from()).thenReturn(user);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123L);
        when(user.id()).thenReturn(123L);
        when(user.firstName()).thenReturn("Test");

        // Настраиваем поведение
        when(userRegistry.tryAddNewUser(anyLong(), anyString())).thenReturn(true);

        // Вызываем метод, который хотим протестировать
        boolean result = startCommand.handlerCommand(update);

        // Проверяем результат
        Assertions.assertTrue(result);

        // Проверяем, что методы были вызваны с правильными аргументами
        verify(userRegistry).tryAddNewUser(123L, "Test");
        // chatClient.postRegisterChat(chatId); // Эта строка была закомментирована
        verify(bot).execute(Mockito.any());
    }
}


