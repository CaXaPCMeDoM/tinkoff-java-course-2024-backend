package edu.java.repository.jpa;

import edu.java.dto.ChatLinkDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JpaChatLinkRepository extends JpaRepository<ChatLinkDto, Long> {
    @Transactional
    @Query("SELECT c.chatLinkId.chat.chatId FROM ChatLinkDto c WHERE c.chatLinkId.link.linkId = :urlId")
    List<Long> findChatIdsByUrlId(@Param("urlId") Long urlId);

    @Transactional
    @Modifying
    @Query(
        "DELETE FROM ChatLinkDto cl "
            + "WHERE cl.chatLinkId.chat.chatId = :chatId "
            + "AND cl.chatLinkId.link.linkId = :urlId")
    void deleteByChatIdAndLinkId(@Param("chatId") Long chatId, @Param("urlId") Long urlId);

    // void deleteByChatLinkId_Chat_ChatIdAndChatLinkId_Link_LinkId(Long chatId, Long linkId);
    @Transactional
    @Query("SELECT c.chatLinkId.link.linkId FROM ChatLinkDto c WHERE c.chatLinkId.chat.chatId = :chatId")
    List<Long> findLinkIdsByChatId(@Param("chatId") Long chatId);
}
