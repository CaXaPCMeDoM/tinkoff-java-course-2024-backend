package edu.java.repository.jpa;

import edu.java.dto.jpa.JpaChatLinkDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChatLinkRepository extends JpaRepository<JpaChatLinkDto, Long> {
    List<Long> findAllByChatLinkId_Link_LinkId(Long chatLinkId_link_linkId);
//    @Query("SELECT c.chatLinkId.Chat.chatId FROM JpaChatLinkDto c WHERE c.chatLinkId.Link.linkId = :linkId")
//    List<Long> findChatIdsByLinkId(@Param("linkId") Long linkId);
}
