package edu.java;

import lombok.Data;

@Data
public class ApiErrorResponse {
    private String description;
    private String code;
    private String errorId;
}
