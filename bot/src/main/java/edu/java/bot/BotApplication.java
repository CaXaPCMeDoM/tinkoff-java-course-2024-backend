package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.services.bot.Bot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BotApplication.class, args);
        ApplicationConfig config = context.getBean(ApplicationConfig.class);

        String token = config.telegramToken();
        // TelegramBot telegramBot = new TelegramBot(token);

        Bot bot = new Bot(token);

        bot.initBot();


        /*telegramBot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> list) {
                for (Update update: list) {
                    System.out.println(update.message().from().languageCode().getBytes(

                    ));
                    SendMessage sendMessage = new SendMessage(update.message().from().id(), "Привет");
                    telegramBot.execute(sendMessage);
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });*/
    }
}

