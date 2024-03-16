package edu.java.bot.web.controller;

import edu.java.ApiErrorResponse;
import edu.java.bot.services.bot.Bot;
import edu.java.bot.web.controller.dto.LinkUpdateRequest;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BotController.class);
    @Autowired Bot bot;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Обновление обработано"),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",

                     content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/updates")
    public void postUpdate(@Valid @RequestBody LinkUpdateRequest linkUpdateRequest) {
        LOGGER.info("Был вызван /updates. Пришли id: \n");
        for (Long chatId : linkUpdateRequest.getTgChatIds()) {
            LOGGER.info("id: " + chatId.toString() + "url: " + linkUpdateRequest.getUrl() + "\n");
            bot.sendAMessageAboutUpdatingLinks(chatId, linkUpdateRequest);
        }
    }
}
