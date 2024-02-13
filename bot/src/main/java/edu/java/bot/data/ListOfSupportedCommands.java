package edu.java.bot.data;

import org.apache.kafka.common.protocol.types.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListOfSupportedCommands {
    private static List<String> commands = new ArrayList<>();

    public void addCommandName(String command) {
        commands.add(command + "\n");
        return;
    }

    public List<String> getCommandsName() {
        return commands;
    }
}
