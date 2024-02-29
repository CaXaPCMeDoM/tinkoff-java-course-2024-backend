package edu.java.bot.web.controller.dto;

import lombok.Data;
import lombok.Getter;
import java.util.List;

@Data
@Getter
public class ApiErrorResponse {
    private String description;
    private String code;
    private String exceptionName;
    private String exceptionMessage;
    private List<String> stacktrace;
}
