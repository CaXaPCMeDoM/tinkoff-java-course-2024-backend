package edu.java.service;

import java.util.List;

public interface ChatLinkService {
    List<Long> getChatidsByUrlId(Long urlId);
}
