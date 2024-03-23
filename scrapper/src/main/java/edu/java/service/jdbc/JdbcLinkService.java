package edu.java.service.jdbc;

import edu.java.dao.jdbc.ChatLinkDao;
import edu.java.dao.jdbc.LinkDao;
import edu.java.dto.LinkDto;
import edu.java.dto.jdbc.JdbcChatLinkDto;
import edu.java.internal.controllers.dto.ListLinksResponse;
import edu.java.service.LinkService;
import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class JdbcLinkService implements LinkService {
    private LinkDto linkDto;
    private final LinkDao linkDao;

    private JdbcChatLinkDto jdbcChatLinkDto;
    private final ChatLinkDao chatLinkDao;

    public JdbcLinkService(LinkDao linkDao, ChatLinkDao chatLinkDao) {
        this.linkDao = linkDao;
        this.chatLinkDao = chatLinkDao;
    }

    @Override
    public Long add(long tgChatId, URI url) {
        linkDto = new LinkDto(url.toString(), Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), String.valueOf(tgChatId));
        linkDao.add(linkDto);
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
    public List<LinkDto> listAll() {
        return linkDao.findAll();
    }
}
