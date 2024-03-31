package edu.java.scrapper;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.external.client.github.client.GitHubClient;
import edu.java.external.client.github.responceDTO.ReposResponse;
import edu.java.scrapper.db.IntegrationEnvironment;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WireMockTest
public class GitHubClientTest extends IntegrationEnvironment {
    @Autowired
    private GitHubClient gitHubClient;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort().dynamicPort())
        .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("github.base-url", wireMockServer::baseUrl);
    }

    @Test
    public void checkingTheCorrectnessOfTheData() throws IOException {
        String owner = "sanyarnd";
        String repo = "java-course-2023-backend-template";
        String question_id = "17432735";
        String jsonContent = new String(Files.readAllBytes(Paths.get("src/test/java/edu/java/scrapper/data/testDataGitHub.json")));
        wireMockServer.stubFor(WireMock.get(urlEqualTo("/repos/sanyarnd/java-course-2023-backend-template/events"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(jsonContent)));

        ReposResponse response = gitHubClient.getRepositoryData(owner, repo).block();

        Assertions.assertEquals(35982505038L, response.getId());
        Assertions.assertEquals(OffsetDateTime.parse("2024-02-25T10:19:29Z"), response.getCreatedAt());
    }
}
