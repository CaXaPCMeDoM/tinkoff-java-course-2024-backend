package edu.java.internal.controllers.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LinkResponse {
    @NotEmpty
    private Long id;
    @NotEmpty
    private String url;
}
