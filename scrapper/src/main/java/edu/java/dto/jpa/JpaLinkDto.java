package edu.java.dto.jpa;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "link")
public class JpaLinkDto {
    @Id
    @Column(name = "url_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long linkId;
    @Column(name = "url")
    private String url;
    @Column(name = "last_check_time")
    private Timestamp lastCheckTime;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "created_by")
    private String createdBy;

    public JpaLinkDto(Long chatId, String url) {
        this.url = url;
        lastCheckTime = Timestamp.valueOf(LocalDateTime.now());
        createdAt = Timestamp.valueOf(LocalDateTime.now());
        createdBy = chatId.toString();
    }
}
