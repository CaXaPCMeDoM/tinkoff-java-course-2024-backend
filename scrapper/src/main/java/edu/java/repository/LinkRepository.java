package edu.java.repository;

import edu.java.dto.LinkDto;

public interface LinkRepository {
    void add(LinkDto linkDto);

    LinkDto findUrlIdByLink(String link);

    //    void remove(long tgChatId, URI url);
//    void updateLastCheckTimeByLink(Long linkId);
}
