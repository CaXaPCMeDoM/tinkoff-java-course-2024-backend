package edu.java.bot.services.url.strategy;

import org.springframework.stereotype.Service;
import java.net.URI;

@Service
public interface DomainSetCommand {

    void startTracking(Long userId, URI url);

    void stopTracking(Long userId, URI url);
}
