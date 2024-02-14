package edu.java.bot.services.url.strategy.resources.support.stackoverflow;

import edu.java.bot.services.url.strategy.IDomainSetCommand;

public class StackOverflowCommand implements IDomainSetCommand {
    @Override
    public void startTracking(String userId, String url) {
        URL_REPOSITORY.addInMemoryList(userId, url);
    }

    @Override
    public void stopTracking(String userId, String url) {
        URL_REPOSITORY.deleteByUserId(userId, url);
    }
}
