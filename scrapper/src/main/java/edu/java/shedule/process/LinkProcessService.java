package edu.java.shedule.process;

import edu.java.external.client.github.client.GitHubClient;
import edu.java.external.client.stackoverflow.client.StackOverflowClient;
import edu.java.internal.client.UpdateClient;
import edu.java.internal.client.dto.LinkClientUpdateRequest;
import edu.java.model.LinkData;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkProcessService {
    private final UpdateClient updateClient;

    private Long counter = 0L;
    private static final Long MAX_COUNTER = 100000L;

    private LinkData linkData = new LinkData();

    private LinkClientUpdateRequest linkClientUpdateRequest;
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkProcessService.class);
    private final ConcurrentMap<String, String> linkResponses = new ConcurrentHashMap<>();
    private final List<LinkProcess> linkProcessors;

    @Autowired
    public LinkProcessService(GitHubClient gitHubClient, StackOverflowClient stackOverflowClient,
        UpdateClient updateClient
    ) {
        linkProcessors = Arrays.asList(
            new GitHubLinkProcessor(gitHubClient),
            new StackOverflowLinkProcessor(stackOverflowClient)
        );
        this.updateClient = updateClient;
    }

    public boolean processLink(String link) {
        for (LinkProcess linkProcessor : linkProcessors) {
            String response = linkProcessor.process(link);
            if (!linkProcessor.canProcess(link) && response == null) {
                continue;
            }
            handleResponse(link, response);
            return true;
        }
        return false;
    }

    private void handleResponse(String link, String response) {
        String previousResponse = linkResponses.put(link, response);
        if (previousResponse != null && !previousResponse.equals(response)) {
            LOGGER.info("Данные по ссылке {} изменились! Ответ: {}", link, previousResponse);
            List<Long> chatIds = linkData.getIdsWithSameUrl(link);
            linkClientUpdateRequest = LinkClientUpdateRequest.builder()
                .id(counter % MAX_COUNTER)
                .url(link)
                .tgChatIds(chatIds)
                .build();
            if (counter >= MAX_COUNTER) {
                counter = 0L;
            }
            updateClient.postRepositoryData(linkClientUpdateRequest);
        }
    }
}
