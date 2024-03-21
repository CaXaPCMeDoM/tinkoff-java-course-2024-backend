package edu.java.dto.jdbc;

import lombok.Data;

@Data
public class JdbcChatDto {
    private Long chatId;

    public JdbcChatDto(Long chatId) {
        this.chatId = chatId;
    }
}
