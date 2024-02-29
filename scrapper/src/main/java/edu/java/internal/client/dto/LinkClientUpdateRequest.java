package edu.java.internal.client.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Builder
public class LinkClientUpdateRequest {
    private Long id;
    private String url;
    private String description;
    List<Long> tgChatIds;
}
