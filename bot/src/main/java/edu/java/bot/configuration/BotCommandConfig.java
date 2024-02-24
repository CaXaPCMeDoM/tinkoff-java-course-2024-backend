package edu.java.bot.configuration;

import com.pengrad.telegrambot.model.BotCommand;
import edu.java.bot.services.command.HelpCommand;
import edu.java.bot.services.command.ListCommand;
import edu.java.bot.services.command.StartCommand;
import edu.java.bot.services.command.TrackCommand;
import edu.java.bot.services.command.UntrackCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotCommandConfig {
    @Bean
    public BotCommand startCommandBean() {
        return new BotCommand(new StartCommand().getCommandName(), new StartCommand().getDescription());
    }

    @Bean
    public BotCommand listCommandBean() {
        return new BotCommand(new ListCommand().getCommandName(), new ListCommand().getDescription());
    }

    @Bean
    public BotCommand trackCommandBean() {
        return new BotCommand(new TrackCommand().getCommandName(), new TrackCommand().getDescription());
    }

    @Bean
    public BotCommand untrackCommandBean() {
        return new BotCommand(new UntrackCommand().getCommandName(), new UntrackCommand().getDescription());
    }

    @Bean
    public BotCommand helpCommandBean() {
        return new BotCommand(new HelpCommand().getCommandName(), new HelpCommand().getDescription());
    }
}
