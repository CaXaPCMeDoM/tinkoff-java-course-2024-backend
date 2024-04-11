package edu.java.bot.web.controller;

import edu.java.ApiErrorResponse;
import edu.java.bot.services.updates.NotificationService;
import edu.java.bot.web.controller.dto.LinkUpdateRequest;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BotController {
    private final NotificationService notificationService;

    public BotController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Обновление обработано"),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",

                     content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/updates")
    public void postUpdate(@Valid @RequestBody LinkUpdateRequest linkUpdateRequest) {
        log.info("Был вызван /updates");
        notificationService.sendUpdate(linkUpdateRequest);
    }
}
