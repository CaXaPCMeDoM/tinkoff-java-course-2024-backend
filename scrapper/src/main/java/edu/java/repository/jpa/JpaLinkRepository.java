package edu.java.repository.jpa;

import edu.java.dto.jpa.JpaLinkDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLinkRepository extends JpaRepository<JpaLinkDto, Long> {
}
