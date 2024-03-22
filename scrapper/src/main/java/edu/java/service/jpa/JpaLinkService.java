package edu.java.service.jpa;

import edu.java.dto.jdbc.JdbcLinkDto;
import edu.java.dto.jpa.ChatLinkId;
import edu.java.dto.jpa.JpaChatDto;
import edu.java.dto.jpa.JpaChatLinkDto;
import edu.java.dto.jpa.JpaLinkDto;
import edu.java.internal.controllers.dto.ListLinksResponse;
import edu.java.repository.jpa.JpaChatLinkRepository;
import edu.java.repository.jpa.JpaChatRepository;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class JpaLinkService implements LinkService {
    @Value("${link.update.delay}")
    private long delay;
    private JpaLinkRepository jpaLinkRepository;
    private JpaChatLinkRepository jpaChatLinkRepository;
    private JpaChatRepository jpaChatRepository;

    @Override
    public Long add(long tgChatId, URI url) {
        try {
            JpaLinkDto jpaLinkDto = new JpaLinkDto(tgChatId, url.toString());
            JpaLinkDto jpaLinkDtoWithLinkId = jpaLinkRepository.save(jpaLinkDto);
            Long linkId = jpaLinkDtoWithLinkId.getLinkId();

            Optional<JpaChatDto> optionalJpaChatDto = jpaChatRepository.findById(tgChatId);
            if (optionalJpaChatDto.isPresent()) {
                JpaChatDto jpaChatDto = optionalJpaChatDto.get();

                ChatLinkId chatLinkId = new ChatLinkId(jpaChatDto, jpaLinkDto);
                JpaChatLinkDto jpaChatLinkDto = new JpaChatLinkDto(chatLinkId);

                jpaChatLinkRepository.save(jpaChatLinkDto);
                log.info("Исключение не было вызвано. Метод add отработал в JpaLinkService");
                return linkId;
            } else {
                log.warn("Chat_id не найден");
            }
        } catch (NoSuchElementException e) {
            log.warn("Link Service Не нашел в бд по вызову findBy");
        }
        return null;
    }

    @Override
    public void remove(long tgChatId, URI url) {
        JpaLinkDto jpaLinkDto = jpaLinkRepository.findByUrl(url.toString());

        if (jpaLinkDto != null) {
            Long linkId = jpaLinkDto.getLinkId();

            jpaChatLinkRepository.deleteByChatLinkId_Chat_ChatIdAndChatLinkId_Link_LinkId(tgChatId, linkId);
        }
    }

    @Override
    public ListLinksResponse listAllByChatId(long tgChatId) {
        List<Long> urlIds = jpaChatLinkRepository.findAllByChatLinkId_Chat_ChatId(tgChatId);
        //List<Long> urlIds = jpaChatLinkRepository.findUrlIdsByChatId(tgChatId);
        ListLinksResponse response = new ListLinksResponse();

        for (Long urlId : urlIds) {
            JpaLinkDto jpaLinkDto = jpaLinkRepository.findById(urlId).orElse(null);
            if (jpaLinkDto != null) {
                response.setLinks(Collections.singletonList(jpaLinkDto.getUrl()), urlId);
            }
        }

        return response;
    }

    @Override
    public List<JdbcLinkDto> listAll() {
        LocalDateTime time = LocalDateTime.now().minusMinutes(1);
        Timestamp timestamp = Timestamp.valueOf(time);
        return jpaLinkRepository.findAllByLastCheckTimeBefore(timestamp);
    }
}
