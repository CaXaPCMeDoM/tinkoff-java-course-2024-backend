package edu.java.service;

import edu.java.dto.LinkDto;
import edu.java.internal.controllers.dto.ListLinksResponse;
import java.net.URI;
import java.util.List;

public interface LinkService {
    Long add(long tgChatId, URI url);

    void remove(long tgChatId, URI url);

    ListLinksResponse listAllByChatId(long tgChatId);

    List<LinkDto> listAll();
}
