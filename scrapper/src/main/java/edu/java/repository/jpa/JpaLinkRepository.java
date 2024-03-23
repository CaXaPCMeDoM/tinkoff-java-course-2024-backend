package edu.java.repository.jpa;

import edu.java.dto.LinkDto;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLinkRepository extends JpaRepository<LinkDto, Long> {
    LinkDto findByUrl(String url);
    List<LinkDto> findAllByLastCheckTimeBefore(Timestamp lastCheckTime);

    @Modifying
    @Query("UPDATE LinkDto l SET l.lastCheckTime = CURRENT_TIMESTAMP WHERE l.linkId = :linkId")
    void updateLastCheckTimeByLink(@Param("linkId") Long linkId);
}
