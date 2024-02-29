package edu.java.bot.web.client.dto.chat;

import java.util.List;
import lombok.Data;

@Data
public class ExceptionMessageResponse {
    private String description;
    private String code;
    private String exceptionName;
    private String exceptionMessage;
    private List<String> stacktrace;
}
