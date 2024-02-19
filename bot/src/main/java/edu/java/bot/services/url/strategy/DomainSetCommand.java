package edu.java.bot.services.url.strategy;

import org.springframework.stereotype.Service;

@Service
public interface DomainSetCommand {

    void startTracking(String userId, String url);

    void stopTracking(String userId, String url);
}
