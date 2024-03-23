package edu.java.repository.jpa;

import edu.java.dto.jpa.JpaChatLinkDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChatLinkRepository extends JpaRepository<JpaChatLinkDto, Long> {
    @Query("SELECT c.chatLinkId.chat.chatId FROM JpaChatLinkDto c WHERE c.chatLinkId.link.linkId = :urlId")
    List<Long> findChatIdsByUrlId(@Param("urlId") Long urlId);

    @Modifying
    @Query(
        "DELETE FROM JpaChatLinkDto cl "
            + "WHERE cl.chatLinkId.chat.chatId = :chatId "
            + "AND cl.chatLinkId.link.linkId = :urlId")
    void deleteByChatIdAndLinkId(@Param("chatId") Long chatId, @Param("urlId") Long urlId);

    // void deleteByChatLinkId_Chat_ChatIdAndChatLinkId_Link_LinkId(Long chatId, Long linkId);
    @Query("SELECT c.chatLinkId.link.linkId FROM JpaChatLinkDto c WHERE c.chatLinkId.chat.chatId = :chatId")
    List<Long> findLinkIdsByChatId(@Param("chatId") Long chatId);

    @Query("SELECT cl.chatLinkId.link.linkId FROM JpaChatLinkDto cl WHERE cl.chatLinkId.chat.chatId = :chatId")
    List<Long> findUrlIdsByChatId(@Param("chatId") Long chatId);
}
