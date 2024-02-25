package edu.java.shedule;

import edu.java.client.github.DataForRepositoryGitHub;
import edu.java.client.github.ParserForGitHub;
import edu.java.client.github.client.GitHubClient;
import edu.java.client.github.responceDTO.ReposResponce;
import edu.java.client.stackoverflow.DataForRequestStackoverflow;
import edu.java.client.stackoverflow.ParserForStackOverflow;
import edu.java.client.stackoverflow.client.StackOverflowClient;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkProcessService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkProcessService.class);
    @Autowired
    private GitHubClient gitHubClient;
    @Autowired
    private StackOverflowClient stackOverflowClient;
    private final Map<String, String> linkResponses = new ConcurrentHashMap<>();

    public boolean processLink(String link) {
        String response = null;
        if (link.contains("github.com")) {
            DataForRepositoryGitHub dataForRepositoryGitHub;
            try {
                ReposResponce reposResponce = null;
                dataForRepositoryGitHub = ParserForGitHub.getOwnerAndRepo(link);
                if (dataForRepositoryGitHub != null) {
                    reposResponce = gitHubClient.getRepositoryData(
                        dataForRepositoryGitHub.getOwner(),
                        dataForRepositoryGitHub.getRepo()
                    ).block();
                    if (reposResponce != null) {
                        response = reposResponce.toString();
                    }
                }
            } catch (URISyntaxException e) {
                return false;
            }
        } else if (link.contains("stackoverflow.com")) {
            DataForRequestStackoverflow dataForRequestStackoverflow = null;
            try {
                String id = ParserForStackOverflow.checkingIfThisIsAQuestionAndReturningTheId(link);
                if (id != null) {
                    dataForRequestStackoverflow = stackOverflowClient.fetchQuestion(id).block();
                }
                if (dataForRequestStackoverflow != null) {
                    response = dataForRequestStackoverflow.toString();
                }
            } catch (MalformedURLException e) {
                return false;
            }
        } else {
            return false;
        }
        if (response != null) {
            String previousResponse = linkResponses.put(link, response);
            if (previousResponse != null && !previousResponse.equals(response)) {
                LOGGER.info("Данные по ссылке {} изменились! Предыдущий ответ: {}", link, previousResponse);
            }
        }
        return true;
    }
}
