package edu.java.bot.web.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkUpdateRequest {
    @NotNull
    private Long id;
    @NotEmpty
    private String url;
    private String description;
    private String typeOfUpdate;
    @NotEmpty
    private List<Long> tgChatIds;
}
