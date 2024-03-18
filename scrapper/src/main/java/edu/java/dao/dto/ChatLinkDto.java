package edu.java.dao.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatLinkDto {
    private Long chatId;
    private Long urlId;

    public ChatLinkDto(Long chatId, Long urlId) {
        this.chatId = chatId;
        this.urlId = urlId;
    }
}
