package edu.java.bot.web.controller;

import edu.java.bot.services.bot.Bot;
import edu.java.bot.web.controller.dto.ApiErrorResponse;
import edu.java.bot.web.controller.dto.LinkUpdateRequest;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class BotController {
    @Autowired Bot bot;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Обновление обработано"),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",

                     content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/updates")
    public void postUpdate(@Valid @RequestBody LinkUpdateRequest linkUpdateRequest) {
        for (Long chatId : linkUpdateRequest.getTgChatIds()) {
            bot.sendAMessageAboutUpdatingLinks(chatId, linkUpdateRequest.getUrl());
        }
    }
}
