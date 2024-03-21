package edu.java.dto.jdbc;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class JdbcLinkDto {
    private Long linkId;
    private String url;
    private LocalDateTime lastCheckTime;
    private LocalDateTime createdAt;
    private String createdBy;

    public JdbcLinkDto(String url, LocalDateTime lastCheckTime, LocalDateTime createdAt, String createdBy) {
        this.url = url;
        this.lastCheckTime = lastCheckTime;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }
}
