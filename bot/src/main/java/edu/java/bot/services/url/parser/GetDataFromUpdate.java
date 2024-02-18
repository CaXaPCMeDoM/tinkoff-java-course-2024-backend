package edu.java.bot.services.url.parser;

import com.pengrad.telegrambot.model.Update;

public class GetDataFromUpdate {
    private Update update;

    public String userIdString(Update upd) {
        update = upd;
        return update.message().from().id().toString();
    }
}
