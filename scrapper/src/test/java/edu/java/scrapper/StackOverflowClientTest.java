package edu.java.scrapper;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.client.github.DataForRepositoryGitHub;
import edu.java.client.github.client.GitHubClient;
import edu.java.client.stackoverflow.DataForRequestStackoverflow;
import edu.java.client.stackoverflow.Item;
import edu.java.client.stackoverflow.client.StackOverflowClient;
import io.restassured.internal.common.assertion.Assertion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.OffsetDateTime;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WireMockTest
public class StackOverflowClientTest {
    @Autowired
    private StackOverflowClient stackOverflowClient;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort().dynamicPort())
        .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("stackoverflow.base-url", wireMockServer::baseUrl);
    }

    @Test
    void checkingTheCorrectnessOfTheData() {
        String question_id = "17432735";
        String products =
            "{\"items\":[{\"tags\":[\"java\",\"unix-timestamp\"],\"owner\":{\"account_id\":2988131,\"reputation\":1318,\"user_id\":%s,\"user_type\":\"registered\",\"accept_rate\":30,\"profile_image\":\"https://www.gravatar.com/avatar/3198a347ebfa8007b44d9adf42959f35?s=256&d=identicon&r=PG&f=y&so-version=2\",\"display_name\":\"bigData\",\"link\":\"https://stackoverflow.com/users/2536373/bigdata\"},\"is_answered\":true,\"view_count\":170289,\"closed_date\":1489645493,\"accepted_answer_id\":17433005,\"answer_count\":3,\"score\":70,\"last_activity_date\":1617096110,\"creation_date\":1372788431,\"last_edit_date\":1617096110,\"question_id\":17432735,\"link\":\"https://stackoverflow.com/questions/17432735/convert-unix-timestamp-to-date-in-java\",\"closed_reason\":\"Duplicate\",\"title\":\"Convert unix timestamp to date in java\"}],\"has_more\":false,\"quota_max\":300,\"quota_remaining\":299}";
        wireMockServer.stubFor(WireMock.get(urlEqualTo("/questions/17432735?site=stackoverflow"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(products.formatted(question_id))));

        DataForRequestStackoverflow responce = stackOverflowClient.fetchQuestion(question_id).block();
        Assertions.assertEquals(question_id, responce.getItems().get(0).getQuestionId());
        Assertions.assertEquals(
            OffsetDateTime.parse("2021-03-30T09:21:50Z"),
            responce.getItems().get(0).getLastActivityDate()
        );
    }
}


