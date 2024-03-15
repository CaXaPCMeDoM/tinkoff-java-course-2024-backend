package edu.java.internal.controllers;

import edu.java.ApiErrorResponse;
import edu.java.internal.controllers.dto.LinkRequest;
import edu.java.internal.controllers.dto.LinkResponse;
import edu.java.internal.controllers.dto.ListLinksResponse;
import edu.java.service.LinkService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/links")
public class LinkController {
    private LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = LinkResponse.class))),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<LinkResponse> addTrackingLinks(
        @RequestHeader("Tg-Chat-Id") Long id,
        @Valid @RequestBody LinkRequest link
    ) {
        linkService.add(id, link.getLink());

        LinkResponse linkResponse = new LinkResponse();
        linkResponse.setId(id);
        linkResponse.setUrl(link.getLink().toString());

        return ResponseEntity.ok(linkResponse);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ссылки успешно получены",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ListLinksResponse.class))),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<ListLinksResponse> getTrackingLinks(
        @RequestHeader("Tg-Chat-Id") Long id
    ) {
        return ResponseEntity.ok(linkService.listAllByChatId(id));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ссылка успешно убрана",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = LinkResponse.class))),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Ссылка не найдена",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping
    public ResponseEntity<LinkResponse> deleteTrackingLinks(
        @RequestHeader("Tg-Chat-Id") Long id,
        @Valid @RequestBody LinkRequest linkRequest
    ) {
        linkService.remove(id, linkRequest.getLink());

        LinkResponse linkResponse = new LinkResponse();
        linkResponse.setId(id);
        linkResponse.setUrl(linkRequest.getLink().toString());

        return ResponseEntity.ok(linkResponse);
    }
}
