package edu.java.repository.jpa;

import edu.java.dto.jpa.JpaLinkDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JpaLinkRepository extends JpaRepository<JpaLinkDto, Long> {
    JpaLinkDto findByUrl(String url);
    List<JpaLinkDto> findAllByLastCheckTimeBefore(Timestamp lastCheckTime);
}
