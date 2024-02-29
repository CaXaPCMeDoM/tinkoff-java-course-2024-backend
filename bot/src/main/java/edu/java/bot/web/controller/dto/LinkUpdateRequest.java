package edu.java.bot.web.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import java.util.List;

@Builder
@Data
@Getter
public class LinkUpdateRequest {
    @NotEmpty
    private Long id;
    @NotEmpty
    private String url;
    private String description;
    @NotEmpty
    private List<Long> tgChatIds;
}
