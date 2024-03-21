package edu.java.repository.jpa;

import edu.java.dto.jpa.JpaChatLinkDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChatLinkRepository extends JpaRepository<JpaChatLinkDto, Long> {
}
