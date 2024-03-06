package edu.java.internal.controllers.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LinkResponse {
    @NotNull
    private Long id;
    @NotEmpty
    private String url;
}
