package edu.java.dao.dto;

import lombok.Data;

@Data
public class ChatDto {
    private Long chatId;

    public ChatDto(Long chatId) {
        this.chatId = chatId;
    }
}
