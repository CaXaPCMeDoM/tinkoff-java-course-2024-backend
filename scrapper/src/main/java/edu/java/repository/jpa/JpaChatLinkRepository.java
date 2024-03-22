package edu.java.repository.jpa;

import edu.java.dto.jpa.JpaChatLinkDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JpaChatLinkRepository extends JpaRepository<JpaChatLinkDto, Long> {
    List<Long> findAllByChatLinkId_Link_LinkId(Long chatLinkId_link_linkId);
    /*    @Modifying
    @Transactional
    @Query("DELETE FROM JpaChatLinkDto cl WHERE cl.chatLinkId.Chat.chatId = :chatId AND cl.chatLinkId.Link.linkId = :urlId")
    void remove(@Param("chatId") Long chatId, @Param("urlId") Long urlId);
*/
    void deleteByChatLinkId_Chat_ChatIdAndChatLinkId_Link_LinkId(Long chatId, Long linkId);
    List<Long> findAllByChatLinkId_Chat_ChatId(Long chatLinkId_chat_chatId);
    @Query("SELECT cl.chatLinkId.Link.linkId FROM JpaChatLinkDto cl WHERE cl.chatLinkId.Chat.chatId = :chatId")
    List<Long> findUrlIdsByChatId(@Param("chatId") Long chatId);
//    @Query("SELECT c.chatLinkId.Chat.chatId FROM JpaChatLinkDto c WHERE c.chatLinkId.Link.linkId = :linkId")
//    List<Long> findChatIdsByLinkId(@Param("linkId") Long linkId);
}
