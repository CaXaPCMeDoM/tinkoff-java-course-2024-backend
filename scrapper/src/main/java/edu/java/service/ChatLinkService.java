package edu.java.service;

import java.util.List;

public interface ChatLinkService {
    List<Long> getChatIdsByUrlId(Long urlId);
}
