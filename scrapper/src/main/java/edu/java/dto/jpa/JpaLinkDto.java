package edu.java.dto.jpa;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private Long linkId;
    @Column(name = "url")
    private String url;
    @Column(name = "last_check_time")
    private LocalDateTime lastCheckTime;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "created_by")
    private String createdBy;
}
