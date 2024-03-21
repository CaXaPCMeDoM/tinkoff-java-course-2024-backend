package edu.java.dto.jdbc;

import lombok.Data;

@Data
public class JdbcChatLinkDto {
    private Long chatId;
    private Long urlId;

    public JdbcChatLinkDto(Long chatId, Long urlId) {
        this.chatId = chatId;
        this.urlId = urlId;
    }
}
