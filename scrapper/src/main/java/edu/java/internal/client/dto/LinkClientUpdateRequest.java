package edu.java.internal.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@Getter
public class LinkClientUpdateRequest {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("url")
    private String url;

    @JsonProperty("description")
    private String description;

    @JsonProperty("typeOfUpdate")
    private String typeOfUpdate;

    @JsonProperty("tgChatIds")
    private List<Long> tgChatIds;
}
