package edu.java.shedule.process;

import edu.java.external.client.github.DataForRepositoryGitHub;
import edu.java.external.client.github.ParserForGitHub;
import edu.java.external.client.github.client.GitHubClient;
import edu.java.external.service.CommonDataResponseClient;
import java.net.URISyntaxException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
class GitHubLinkProcessor implements LinkProcess {
    private final GitHubClient gitHubClient;

    GitHubLinkProcessor(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    @Override
    public boolean canProcess(String link) {
        return link.contains("github.com");
    }

    @Override
    public CommonDataResponseClient process(String link) {
        try {
            DataForRepositoryGitHub dataForRepositoryGitHub = ParserForGitHub.getOwnerAndRepo(link);
            if (dataForRepositoryGitHub != null) {
                return gitHubClient.getRepositoryData(
                        dataForRepositoryGitHub.getOwner(),
                        dataForRepositoryGitHub.getRepo()
                    ).onErrorResume(e -> Mono.empty())
                    .blockOptional()
                    .orElse(null);
            }
        } catch (URISyntaxException ignored) {
        }
        return null;
    }
}
