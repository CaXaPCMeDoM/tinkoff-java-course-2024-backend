package edu.java.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "link")
public class LinkDto {
    @Id
    @Column(name = "url_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long linkId;
    @Column(name = "url", unique = true, nullable = false)
    private String url;
    @Column(name = "last_check_time", nullable = false)
    private Timestamp lastCheckTime;
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    public LinkDto(Long chatId, String url) {
        this.url = url;
        lastCheckTime = Timestamp.valueOf(LocalDateTime.now());
        createdAt = Timestamp.valueOf(LocalDateTime.now());
        createdBy = chatId.toString();
    }

    public LinkDto(String url, Timestamp lastCheckTime, Timestamp createdAt, String createdBy) {
        this.url = url;
        this.lastCheckTime = lastCheckTime;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }
}
