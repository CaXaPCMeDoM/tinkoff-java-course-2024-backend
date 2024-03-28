package edu.java.service.jdbc;

import edu.java.dao.jdbc.JdbcChatDao;
import edu.java.dao.jdbc.JdbcChatLinkDao;
import edu.java.dao.jdbc.JdbcLinkDao;
import edu.java.dto.ChatDto;
import edu.java.dto.ChatLinkDto;
import edu.java.dto.ChatLinkId;
import edu.java.dto.LinkDto;
import edu.java.internal.controllers.dto.ListLinksResponse;
import edu.java.service.LinkService;
import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class JdbcLinkService implements LinkService {
    private LinkDto linkDto;
    private ChatDto chatDto;
    private final JdbcLinkDao jdbcLinkDao;

    private ChatLinkDto jdbcChatLinkDto;
    private JdbcChatDao jdbcChatDao;
    private final JdbcChatLinkDao jdbcChatLinkDao;

    public JdbcLinkService(JdbcLinkDao jdbcLinkDao, JdbcChatLinkDao jdbcChatLinkDao) {
        this.jdbcLinkDao = jdbcLinkDao;
        this.jdbcChatLinkDao = jdbcChatLinkDao;
    }

    @Override
    public Long add(long tgChatId, URI url) {
        linkDto = new LinkDto(
            url.toString(),
            Timestamp.valueOf(LocalDateTime.now()),
            Timestamp.valueOf(LocalDateTime.now()),
            String.valueOf(tgChatId)
        );
        jdbcLinkDao.add(linkDto);
        chatDto = jdbcChatDao.findByChatId(tgChatId);
        if (chatDto != null) {
            jdbcChatLinkDto = new ChatLinkDto(new ChatLinkId(chatDto, linkDto));
            jdbcChatLinkDao.add(jdbcChatLinkDto);

            return linkDto.getLinkId();
        } else {
            return null;
        }
    }

    @Override
    public void remove(long tgChatId, URI url) {
        Long urlId = jdbcLinkDao.getIdByUrl(url.toString());

        if (urlId != null) {
            jdbcChatLinkDao.remove(tgChatId, urlId);
        }
    }

    @Override
    public ListLinksResponse listAllByChatId(long tgChatId) {
        return jdbcChatLinkDao.getUrlByChatId(tgChatId);
    }

    @Override
    public List<LinkDto> listAll() {
        return jdbcLinkDao.findAll();
    }
}
