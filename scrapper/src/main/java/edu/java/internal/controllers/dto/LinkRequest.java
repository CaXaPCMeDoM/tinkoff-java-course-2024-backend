package edu.java.internal.controllers.dto;

import jakarta.validation.constraints.NotNull;
import java.net.URI;
import lombok.Data;

@Data
public class LinkRequest {
    @NotNull
    private URI link;
}
