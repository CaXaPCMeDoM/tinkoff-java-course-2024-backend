package edu.java.client.github.responceDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class ReposResponce {
    @JsonProperty("id")
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
