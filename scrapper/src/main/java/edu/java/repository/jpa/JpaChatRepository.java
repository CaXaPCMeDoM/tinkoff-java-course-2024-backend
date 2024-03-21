package edu.java.repository.jpa;

import edu.java.dto.jpa.JpaChatDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChatRepository extends JpaRepository<JpaChatDto, Long> {
}
