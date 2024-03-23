package edu.java.my.exception;

public class ChatNotFoundException extends Exception{
    public ChatNotFoundException(Long chatId){
        super(String.format("Чат с ID %s не найден", chatId));
    }
}
