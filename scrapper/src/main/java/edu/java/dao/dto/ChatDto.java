package edu.java.dao.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatDto {
    private Long chatId;

    public ChatDto(Long chatId) {
        this.chatId = chatId;
    }
}
