package edu.java.updates.process;

import edu.java.dto.LinkDto;
import edu.java.external.client.github.client.GitHubClient;
import edu.java.external.client.stackoverflow.client.StackOverflowClient;
import edu.java.external.service.CommonDataResponseClient;
import edu.java.internal.client.dto.LinkClientUpdateRequest;
import edu.java.service.ChatLinkService;
import edu.java.service.LinkUpdater;
import edu.java.updates.notification.NotificationService;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkProcessService {
    private final NotificationService notificationService;

    private final ChatLinkService chatLinkService;
    private final LinkUpdater linkUpdater;

    private LinkClientUpdateRequest linkClientUpdateRequest;
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkProcessService.class);
    private final List<LinkProcess> linkProcessors;

    @Autowired
    public LinkProcessService(
        GitHubClient gitHubClient,
        StackOverflowClient stackOverflowClient,
        NotificationService notificationService,
        ChatLinkService chatLinkService,
        LinkUpdater linkUpdater
    ) {
        this.notificationService = notificationService;
        this.chatLinkService = chatLinkService;
        this.linkUpdater = linkUpdater;
        linkProcessors = Arrays.asList(
            new GitHubLinkProcessor(gitHubClient),
            new StackOverflowLinkProcessor(stackOverflowClient)
        );
    }

    public boolean processLink(LinkDto linkDto) {
        for (LinkProcess linkProcessor : linkProcessors) {
            CommonDataResponseClient response = linkProcessor.process(linkDto.getUrl());
            if (response != null && linkProcessor.canProcess(linkDto.getUrl())) {
                handleResponse(linkDto, response);
                linkUpdater.updateLinkLastCheckTime(linkDto.getLinkId());
                return true;
            }
        }
        return false;
    }

    private void handleResponse(LinkDto linkDto, CommonDataResponseClient response) {
        LocalDateTime linkCreate = linkDto.getLastCheckTime().toLocalDateTime();
        OffsetDateTime timeFromResponse = response.getTimeLastModified();

        ZoneId zoneId = ZoneId.systemDefault();
        OffsetDateTime linkCreateOffset = linkCreate.atZone(zoneId).toOffsetDateTime();

        if (linkCreateOffset.isBefore(timeFromResponse)) {
            LOGGER.info(
                "Данные по ссылке {} изменились! Ответ: {}",
                linkDto.getUrl(),
                timeFromResponse
            );

            List<Long> chatIds = chatLinkService.getChatIdsByUrlId(linkDto.getLinkId());
            linkClientUpdateRequest = LinkClientUpdateRequest.builder()
                .id(linkDto.getLinkId())
                .url(linkDto.getUrl())
                .tgChatIds(chatIds)
                .typeOfUpdate(response.getTypeOfUpdate())
                .build();
            notificationService.sender(linkClientUpdateRequest);
        }
    }
}
