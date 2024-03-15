package edu.java.service;

import edu.java.internal.controllers.dto.ListLinksResponse;
import java.net.URI;

public interface LinkService {
    Long add(long tgChatId, URI url);

    void remove(long tgChatId, URI url);

    ListLinksResponse listAll(long tgChatId);
}
