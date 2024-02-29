package edu.java.bot.services.url.strategy;

import java.net.URI;
import org.springframework.stereotype.Service;

@Service
public interface DomainSetCommand {

    void startTracking(Long userId, URI url);

    void stopTracking(Long userId, URI url);
}
