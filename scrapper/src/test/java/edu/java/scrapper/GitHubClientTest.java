package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitHubClientTest {

    @Autowired
    private GitHubClient client;

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort().dynamicPort())
        .build();

    private int wiremockServerPort = 8089;

    private WireMockServer wireMockServer;

    @Before
    public void setup() {
        wireMockServer = new WireMockServer(wireMockConfig().port(wiremockServerPort));
        wireMockServer.start();
        WireMock.configureFor("localhost", wiremockServerPort);
    }

    @After
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void fetchQuestion() throws MalformedURLException {
        String owner = "sanyarnd";
        Long id = 35884156443L;
        String repo = "java-course-2023-backend-template";
        OffsetDateTime created_at = OffsetDateTime.parse("2024-02-21T19:07:17Z");

        stubFor(get(urlEqualTo("/repos/" + owner + "?site=stackoverflow"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
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

    }
}
