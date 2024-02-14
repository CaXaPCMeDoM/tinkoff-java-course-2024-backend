package edu.java.bot.services.bot;

import com.pengrad.telegrambot.TelegramBot;

public class StaticBotInstance {
    public static TelegramBot telegramBot;

    // Приватный конструктор
    private StaticBotInstance() {
        // Этот конструктор не предназначен для вызова
        throw new AssertionError("staticBotInstance class should not be instantiated.");
    }
}
