package edu.java.service.jdbc;

import edu.java.dao.jdbc.ChatLinkDao;
import edu.java.dao.jdbc.LinkDao;
import edu.java.dto.jdbc.JdbcChatLinkDto;
import edu.java.dto.jdbc.JdbcLinkDto;
import edu.java.internal.controllers.dto.ListLinksResponse;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import edu.java.service.LinkService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class JdbcLinkService implements LinkService {
    private JdbcLinkDto jdbcLinkDto;
    private final LinkDao linkDao;

    private JdbcChatLinkDto jdbcChatLinkDto;
    private final ChatLinkDao chatLinkDao;

    public JdbcLinkService(LinkDao linkDao, ChatLinkDao chatLinkDao) {
        this.linkDao = linkDao;
        this.chatLinkDao = chatLinkDao;
    }

    @Override
    public Long add(long tgChatId, URI url) {
        jdbcLinkDto = new JdbcLinkDto(url.toString(), LocalDateTime.now(), LocalDateTime.now(), String.valueOf(tgChatId));
        linkDao.add(jdbcLinkDto);
        Long linkId = linkDao.getIdByUrl(url.toString());
        jdbcChatLinkDto = new JdbcChatLinkDto(tgChatId, linkId);
        chatLinkDao.add(jdbcChatLinkDto);

        return linkId;
    }

    @Override
    public void remove(long tgChatId, URI url) {
        Long urlId = linkDao.getIdByUrl(url.toString());

        if (urlId != null) {
            chatLinkDao.remove(tgChatId, urlId);
        }
    }

    @Override
    public ListLinksResponse listAllByChatId(long tgChatId) {
        return chatLinkDao.getUrlByChatId(tgChatId);
    }

    @Override
    public List<JdbcLinkDto> listAll() {
        return linkDao.findAll();
    }
}
