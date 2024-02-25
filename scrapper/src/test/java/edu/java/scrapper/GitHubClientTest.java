package edu.java.scrapper;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WireMockTest
public class GitHubClientTest {
    @Autowired
    private WebTestClient webTestClient;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort().dynamicPort())
        .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("github.base-url", wireMockServer::baseUrl);
    }

    @Test
    void checkingTheCorrectnessOfTheData() throws IOException {
        String question_id = "17432735";
        String jsonContent = new String(Files.readAllBytes(Paths.get("src/test/java/edu/java/scrapper/data/testDataGitHub.json")));
        wireMockServer.stubFor(WireMock.get(urlEqualTo("/repos/sanyarnd/java-course-2023-backend-template/events"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(jsonContent)));

        webTestClient.get().uri("/api/github/repos/sanyarnd/java-course-2023-backend-template/events")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isEqualTo(35982505038L)
            .jsonPath("$.created_at").isEqualTo("2024-02-25T10:19:29Z");
    }
}
