package edu.java.service.jdbc;

import edu.java.dao.jdbc.JdbcChatLinkDao;
import edu.java.dao.jdbc.JdbcLinkDao;
import edu.java.dto.LinkDto;
import edu.java.dto.jdbc.JdbcChatLinkDto;
import edu.java.internal.controllers.dto.ListLinksResponse;
import edu.java.service.LinkService;
import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class JdbcLinkService implements LinkService {
    private LinkDto linkDto;
    private final JdbcLinkDao jdbcLinkDao;

    private JdbcChatLinkDto jdbcChatLinkDto;
    private final JdbcChatLinkDao jdbcChatLinkDao;

    public JdbcLinkService(JdbcLinkDao jdbcLinkDao, JdbcChatLinkDao jdbcChatLinkDao) {
        this.jdbcLinkDao = jdbcLinkDao;
        this.jdbcChatLinkDao = jdbcChatLinkDao;
    }

    @Transactional
    @Override
    public Long add(long tgChatId, URI url) {
        linkDto = new LinkDto(url.toString(),
            Timestamp.valueOf(LocalDateTime.now()),
            Timestamp.valueOf(LocalDateTime.now()),
            String.valueOf(tgChatId)
        );
        jdbcLinkDao.add(linkDto);
        Long linkId = jdbcLinkDao.getIdByUrl(url.toString());
        jdbcChatLinkDto = new JdbcChatLinkDto(tgChatId, linkId);
        jdbcChatLinkDao.add(jdbcChatLinkDto);

        return linkId;
    }

    @Transactional
    @Override
    public void remove(long tgChatId, URI url) {
        Long urlId = jdbcLinkDao.getIdByUrl(url.toString());

        if (urlId != null) {
            jdbcChatLinkDao.remove(tgChatId, urlId);
        }
    }

    @Transactional
    @Override
    public ListLinksResponse listAllByChatId(long tgChatId) {
        return jdbcChatLinkDao.getUrlByChatId(tgChatId);
    }

    @Override
    public List<LinkDto> listAll() {
        return jdbcLinkDao.findAll();
    }
}
