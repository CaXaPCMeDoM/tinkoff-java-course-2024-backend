package edu.java.service.jpa;

import edu.java.dto.ChatDto;
import edu.java.dto.LinkDto;
import edu.java.dto.jpa.ChatLinkId;
import edu.java.dto.jpa.JpaChatLinkDto;
import edu.java.internal.controllers.dto.ListLinksResponse;
import edu.java.repository.jpa.JpaChatLinkRepository;
import edu.java.repository.jpa.JpaChatRepository;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.service.LinkService;
import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;

@Slf4j
public class JpaLinkService implements LinkService {
    private final JpaLinkRepository jpaLinkRepository;
    private final JpaChatLinkRepository jpaChatLinkRepository;
    private final JpaChatRepository jpaChatRepository;

    public JpaLinkService(
        JpaLinkRepository jpaLinkRepository, JpaChatLinkRepository jpaChatLinkRepository,
        JpaChatRepository jpaChatRepository
    ) {
        this.jpaLinkRepository = jpaLinkRepository;
        this.jpaChatLinkRepository = jpaChatLinkRepository;
        this.jpaChatRepository = jpaChatRepository;
    }

    @Override
    public Long add(long tgChatId, URI url) {
        try {
            LinkDto linkDto = new LinkDto(tgChatId, url.toString());
            LinkDto linkDtoWithLinkId = null;
            try {
                linkDtoWithLinkId = jpaLinkRepository.save(linkDto);
            } catch (DataIntegrityViolationException e) {
                linkDtoWithLinkId = jpaLinkRepository.findByUrl(url.toString());
            }
            Long linkId = linkDtoWithLinkId.getLinkId();
            linkDto.setLinkId(linkId);

            Optional<ChatDto> optionalJpaChatDto = jpaChatRepository.findById(tgChatId);
            if (optionalJpaChatDto.isPresent()) {
                ChatDto chatDto = optionalJpaChatDto.get();

                ChatLinkId chatLinkId = new ChatLinkId(chatDto, linkDto);
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
        LinkDto linkDto = jpaLinkRepository.findByUrl(url.toString());

        if (linkDto != null) {
            Long linkId = linkDto.getLinkId();

            jpaChatLinkRepository.deleteByChatIdAndLinkId(tgChatId, linkId);
        }
    }

    @Override
    public ListLinksResponse listAllByChatId(long tgChatId) {
        List<Long> urlIds = jpaChatLinkRepository.findLinkIdsByChatId(tgChatId);
        ListLinksResponse response = new ListLinksResponse();

        for (Long urlId : urlIds) {
            LinkDto linkDto = jpaLinkRepository.findById(urlId).orElse(null);
            if (linkDto != null) {
                response.setLinks(Collections.singletonList(linkDto.getUrl()), urlId);
            }
        }

        return response;
    }

    @Override
    public List<LinkDto> listAll() {
        LocalDateTime time = LocalDateTime.now().minusMinutes(1);
        Timestamp timestamp = Timestamp.valueOf(time);
        return jpaLinkRepository.findAllByLastCheckTimeBefore(timestamp);
    }
}
