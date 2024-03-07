package edu.java.internal.controllers.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LinkRequest {
    @NotEmpty
    private String link;
}
