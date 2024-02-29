package edu.java.bot.web.client.dto.chat;

import lombok.Data;
import java.util.List;

@Data
public class ExceptionMessageResponse {
    private String description;
    private String code;
    private String exceptionName;
    private String exceptionMessage;
    private List<String> stacktrace;
}
