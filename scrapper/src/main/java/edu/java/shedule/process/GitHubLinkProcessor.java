package edu.java.shedule.process;

import edu.java.external.client.github.DataForRepositoryGitHub;
import edu.java.external.client.github.ParserForGitHub;
import edu.java.external.client.github.client.GitHubClient;
import edu.java.external.client.github.responceDTO.ReposResponse;
import java.net.URISyntaxException;
import edu.java.external.service.CommonDataResponseClient;
import org.springframework.stereotype.Service;

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
                ReposResponse reposResponce = gitHubClient.getRepositoryData(
                    dataForRepositoryGitHub.getOwner(),
                    dataForRepositoryGitHub.getRepo()
                ).block();
                if (reposResponce != null) {
                    return reposResponce;
                }
            }
        } catch (URISyntaxException ignored) {
        }
        return null;
    }
}
