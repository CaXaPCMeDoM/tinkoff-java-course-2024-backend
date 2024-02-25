package edu.java.client.github.responceDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReposResponce {
    private Long id;
    @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    public ReposResponce() {
    }

    @Override
    public String toString() {
        return String.valueOf(id) + "\n" + createdAt.toString();
    }
}
