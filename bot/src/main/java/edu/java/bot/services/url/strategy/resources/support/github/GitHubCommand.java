package edu.java.bot.services.url.strategy.resources.support.github;

import edu.java.bot.data.repository.URLRepository;
import edu.java.bot.services.url.strategy.DomainSetCommand;
import org.springframework.stereotype.Component;

@Component
public class GitHubCommand implements DomainSetCommand {
    private final URLRepository URL_REPOSITORY;

    public GitHubCommand(URLRepository URL_REPOSITORY) {
        this.URL_REPOSITORY = URL_REPOSITORY;
    }

    @Override
    public void startTracking(String userId, String url) {
        URL_REPOSITORY.addInMemoryList(userId, url);
    }

    @Override
    public void stopTracking(String userId, String url) {
        URL_REPOSITORY.deleteByUserId(userId, url);
    }
}
