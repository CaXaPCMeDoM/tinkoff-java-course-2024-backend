package edu.java.bot.services.url.strategy;

import edu.java.bot.data.repository.URLRepository;

public interface IDomainSetCommand {
    URLRepository URL_REPOSITORY = new URLRepository();

    void startTracking(String userId, String url);

    void stopTracking(String userId, String url);
}
