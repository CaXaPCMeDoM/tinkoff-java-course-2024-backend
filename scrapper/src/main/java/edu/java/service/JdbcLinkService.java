package edu.java.service;

import edu.java.dao.ChatLinkDao;
import edu.java.dao.LinkDao;
import edu.java.dao.dto.ChatLinkDto;
import edu.java.dao.dto.LinkDto;
import edu.java.internal.controllers.dto.ListLinksResponse;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkService implements LinkService {
    private LinkDto linkDto;
    private final LinkDao linkDao;

    private ChatLinkDto chatLinkDto;
    private final ChatLinkDao chatLinkDao;

    public JdbcLinkService(LinkDao linkDao, ChatLinkDao chatLinkDao) {
        this.linkDao = linkDao;
        this.chatLinkDao = chatLinkDao;
    }

    @Override
    public Long add(long tgChatId, URI url) {
        linkDto = new LinkDto(url.toString(), LocalDateTime.now(), LocalDateTime.now(), String.valueOf(tgChatId));
        linkDao.add(linkDto);
        Long linkId = linkDao.getIdByUrl(url.toString());
        chatLinkDto = new ChatLinkDto(tgChatId, linkId);
        chatLinkDao.add(chatLinkDto);

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
