package edu.java.dao.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkDto {
    private Long linkId;
    private String url;
    private LocalDateTime lastCheckTime;
    private LocalDateTime createdAt;
    private String createdBy;

    public LinkDto(String url, LocalDateTime lastCheckTime, LocalDateTime createdAt, String createdBy) {
        this.url = url;
        this.lastCheckTime = lastCheckTime;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }
}
