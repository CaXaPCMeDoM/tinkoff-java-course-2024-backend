package edu.java.shedule.process;

import edu.java.client.github.client.GitHubClient;
import edu.java.client.stackoverflow.client.StackOverflowClient;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LinkProcessService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkProcessService.class);
    private final Map<String, String> linkResponses = new ConcurrentHashMap<>();
    private final List<LinkProcess> linkProcessors;

    public LinkProcessService(GitHubClient gitHubClient, StackOverflowClient stackOverflowClient) {
        linkProcessors = Arrays.asList(new GitHubLinkProcessor(gitHubClient), new StackOverflowLinkProcessor(stackOverflowClient));
    }

    public boolean processLink(String link) {
        for (LinkProcess linkProcessor : linkProcessors) {
            if (linkProcessor.canProcess(link)) {
                String response = linkProcessor.process(link);
                if (response != null) {
                    String previousResponse = linkResponses.put(link, response);
                    if (previousResponse != null && !previousResponse.equals(response)) {
                        LOGGER.info("Данные по ссылке {} изменились! Ответ: {}", link, previousResponse);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
