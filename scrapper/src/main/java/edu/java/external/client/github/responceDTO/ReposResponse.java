package edu.java.external.client.github.responceDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import edu.java.external.service.CommonDataResponseClient;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReposResponse implements CommonDataResponseClient {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    public ReposResponse() {
    }

    @Override
    public String toString() {
        return String.valueOf(id) + "\n" + createdAt.toString();
    }

    @Override
    public OffsetDateTime getTimeLastModified() {
        return createdAt;
    }
}
