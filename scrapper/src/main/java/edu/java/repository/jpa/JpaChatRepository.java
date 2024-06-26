package edu.java.repository.jpa;

import edu.java.dto.ChatDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChatRepository extends JpaRepository<ChatDto, Long> {
}
