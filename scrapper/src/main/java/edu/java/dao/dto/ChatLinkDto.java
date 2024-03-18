package edu.java.dao.dto;

import lombok.Data;

@Data
public class ChatLinkDto {
    private Long chatId;
    private Long urlId;

    public ChatLinkDto(Long chatId, Long urlId) {
        this.chatId = chatId;
        this.urlId = urlId;
    }
}
