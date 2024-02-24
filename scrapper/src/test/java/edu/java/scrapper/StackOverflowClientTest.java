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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.OffsetDateTime;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WireMockTest
public class StackOverflowClientTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    protected MockMvc mockMvc;

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
        String products = "{\"items\":[{\"question_id\":\"%s\",\"last_activity_date\":\"2021-03-30T09:21:50Z\"}]}";
        wireMockServer.stubFor(WireMock.get(urlEqualTo("/questions/17432735?site=stackoverflow"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(products.formatted(question_id))));

        webTestClient.get().uri("/api/stackoverflow/question/{question_id}", question_id)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.items[0].question_id").isEqualTo(question_id)
            .jsonPath("$.items[0].last_activity_date").isEqualTo("2021-03-30T09:21:50Z");
    }
}





/*@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StackOverflowClientTest {

    @Autowired
    private StackOverflowClient client;

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
        String questionId = "17432735";
        OffsetDateTime lastActivityDate = OffsetDateTime.parse("2021-03-30T09:21:50Z");

        stubFor(get(urlEqualTo("/questions/" + questionId + "?site=stackoverflow"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"items\":[{\"question_id\":\"" + questionId + "\",\"last_activity_date\":\"" + lastActivityDate + "\"}]}")));

        Mono<DataForRequestStackoverflow> result = client.fetchQuestion("https://stackoverflow.com/questions/" + questionId);

        StepVerifier.create(result)
            .assertNext(data -> {
                assertEquals(questionId, data.getItems().get(0).getQuestion_id());
                assertEquals(lastActivityDate, data.getItems().get(0).getLast_activity_date());
            })
            .verifyComplete();
    }
}*/


