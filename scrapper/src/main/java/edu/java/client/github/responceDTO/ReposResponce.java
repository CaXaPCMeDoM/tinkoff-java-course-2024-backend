package edu.java.client.github.responceDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReposResponce {
    private Long id;
    private OffsetDateTime created_at;

    public ReposResponce() {
    }

    @Override
    public String toString() {
        return String.valueOf(id) + "\n" + created_at.toString();
    }
}
