package edu.java.dao.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkDto {
    private Long urlId;
    private String url;
    private LocalDateTime lastCheckTime;
    private LocalDateTime createdAt;
    private String createdBy;

    public LinkDto(Long urlId, String url, LocalDateTime lastCheckTime, LocalDateTime createdAt, String createdBy) {
        this.urlId = urlId;
        this.url = url;
        this.lastCheckTime = lastCheckTime;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }
}
