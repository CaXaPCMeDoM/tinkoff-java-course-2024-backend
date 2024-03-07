package edu.java.bot.web.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@Getter
public class LinkUpdateRequest {
    @NotNull
    private Long id;
    @NotEmpty
    private String url;
    private String description;
    @NotEmpty
    private List<Long> tgChatIds;
}
