package edu.java.shedule.process;

import edu.java.client.github.DataForRepositoryGitHub;
import edu.java.client.github.ParserForGitHub;
import edu.java.client.github.client.GitHubClient;
import edu.java.client.github.responceDTO.ReposResponce;
import java.net.URISyntaxException;
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
    public String process(String link) {
        try {
            DataForRepositoryGitHub dataForRepositoryGitHub = ParserForGitHub.getOwnerAndRepo(link);
            if (dataForRepositoryGitHub != null) {
                ReposResponce reposResponce = gitHubClient.getRepositoryData(
                    dataForRepositoryGitHub.getOwner(),
                    dataForRepositoryGitHub.getRepo()
                ).block();
                if (reposResponce != null) {
                    return reposResponce.toString();
                }
            }
        } catch (URISyntaxException e) {
            return null;
        }
        return null;
    }
}
