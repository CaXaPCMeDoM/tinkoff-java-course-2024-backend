package edu.java.bot.services.url.strategy.resources.support.github;

import edu.java.bot.data.repository.URLRepository;
import edu.java.bot.services.url.strategy.DomainSetCommand;
import org.springframework.stereotype.Component;

@Component
public class GitHubCommand implements DomainSetCommand {
    private final URLRepository urlRepository;

    public GitHubCommand(URLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public void startTracking(String userId, String url) {
        urlRepository.addInMemoryList(userId, url);
    }

    @Override
    public void stopTracking(String userId, String url) {
        urlRepository.deleteByUserId(userId, url);
    }
}
