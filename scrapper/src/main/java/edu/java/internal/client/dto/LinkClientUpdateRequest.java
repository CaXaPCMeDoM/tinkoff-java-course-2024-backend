package edu.java.internal.client.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LinkClientUpdateRequest {
    private Long id;
    private String url;
    private String description;
    List<Long> tgChatIds;
}
