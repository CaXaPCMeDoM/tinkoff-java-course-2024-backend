package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.client.github.client.GitHubClient;
import edu.java.client.github.responceDTO.ReposResponce;
import edu.java.client.stackoverflow.DataForRequestStackoverflow;
import edu.java.client.stackoverflow.client.StackOverflowClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.net.MalformedURLException;
import java.time.OffsetDateTime;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WireMockTest
public class GitHubClientTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private MockMvc mockMvc;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort().dynamicPort())
        .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("github.base-url", wireMockServer::baseUrl);
    }

    @Test
    public void fetchQuestion() {
        String owner = "sanyarnd";
        String id = "35884156443";
        String repo = "java-course-2023-backend-template";
        OffsetDateTime created_at = OffsetDateTime.parse("2024-02-21T19:07:17Z");

        wireMockServer.stubFor(WireMock.get(urlEqualTo("/repos/" + owner + repo + "/events"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("[\n" +
                    "  {\n" +
                    "    \"id\": \"35884156443\",\n" +
                    "    \"type\": \"PushEvent\",\n" +
                    "    \"actor\": {\n" +
                    "      \"id\": 114598862,\n" +
                    "      \"login\": \"CaXaPCMeDoM\",\n" +
                    "      \"display_login\": \"CaXaPCMeDoM\",\n" +
                    "      \"gravatar_id\": \"\",\n" +
                    "      \"url\": \"https://api.github.com/users/CaXaPCMeDoM\",\n" +
                    "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/114598862?\"\n" +
                    "    },\n" +
                    "    \"repo\": {\n" +
                    "      \"id\": 550436325,\n" +
                    "      \"name\": \"CaXaPCMeDoM/Lab2\",\n" +
                    "      \"url\": \"https://api.github.com/repos/CaXaPCMeDoM/Lab2\"\n" +
                    "    },\n" +
                    "    \"payload\": {\n" +
                    "      \"repository_id\": 550436325,\n" +
                    "      \"push_id\": 17200057440,\n" +
                    "      \"size\": 1,\n" +
                    "      \"distinct_size\": 1,\n" +
                    "      \"ref\": \"refs/heads/main\",\n" +
                    "      \"head\": \"e974a52218c55bcf609ab00b3aefbcaedc9cfcf2\",\n" +
                    "      \"before\": \"d92289d499330878b4a78c7849dccdbf0abe845f\",\n" +
                    "      \"commits\": [\n" +
                    "        {\n" +
                    "          \"sha\": \"e974a52218c55bcf609ab00b3aefbcaedc9cfcf2\",\n" +
                    "          \"author\": {\n" +
                    "            \"email\": \"114598862+CaXaPCMeDoM@users.noreply.github.com\",\n" +
                    "            \"name\": \"Vladislav\"\n" +
                    "          },\n" +
                    "          \"message\": \"Update README.md\",\n" +
                    "          \"distinct\": true,\n" +
                    "          \"url\": \"https://api.github.com/repos/CaXaPCMeDoM/Lab2/commits/e974a52218c55bcf609ab00b3aefbcaedc9cfcf2\"\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    },\n" +
                    "    \"public\": true,\n" +
                    "    \"created_at\": \"2024-02-21T19:07:17Z\"}]")));
        webTestClient.get().uri("api/{github}/{repo}/events", owner, repo)
            .exchange()
            .expectStatus().isOk()
            .expectBody();
    }
}
