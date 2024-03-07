package edu.java.bot.services.url.strategy.resources.support.stackoverflow;

import edu.java.bot.services.url.strategy.DomainSetCommand;
import edu.java.bot.web.client.LinkClient;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StackOverflowCommand implements DomainSetCommand {
    @Autowired LinkClient linkClient;

    @Override
    public void startTracking(Long userId, URI uri) {
        linkClient.postAddTrackingLink(userId, uri);
    }

    @Override
    public void stopTracking(Long userId, URI uri) {
        linkClient.deleteTrackingLink(userId, uri);
    }
}
