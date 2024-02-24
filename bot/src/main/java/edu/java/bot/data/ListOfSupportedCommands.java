package edu.java.bot.data;

import com.pengrad.telegrambot.model.BotCommand;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter @Component
public class ListOfSupportedCommands {
    private List<BotCommand> commands;

    @Autowired
    public ListOfSupportedCommands(List<BotCommand> commands) {
        this.commands = commands;
    }
}
