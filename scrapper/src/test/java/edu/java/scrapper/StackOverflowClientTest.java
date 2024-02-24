package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import edu.java.ScrapperApplication;
import edu.java.client.stackoverflow.DataForRequestStackoverflow;
import edu.java.client.stackoverflow.client.StackOverflowClient;
import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.MalformedURLException;
import java.time.OffsetDateTime;
import java.util.List;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import reactor.test.StepVerifier;
import scala.Product;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WireMockTest
@Import(TestConfig.class)
public class StackOverflowClientTest {
    @Autowired
    private WebTestClient webTestClient;

    private final ObjectMapper mapper = new ObjectMapper();
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());
    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort().dynamicPort())
        .build();

    @DynamicPropertySource
    static void registerMockProperties(DynamicPropertyRegistry registry) {
        registry.add("stackoverflow.base-url", () -> "http://localhost:" + wireMockExtension.getHttpsPort());
    }

    @Test
    public void checkingTheCorrectnessOfTheDataReturn() {
        stubFor(get(urlEqualTo("/questions/17432735?site=stackoverflow"))
            .willReturn(aResponse()
                .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("{\"items\":[{\"question_id\":\"17432735\",\"last_activity_date\":\"2021-03-30T09:21:50Z\"}]}")));
        webTestClient.get().uri("/api/stackoverflow/question/17432735")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .json("{\"items\":[{\"question_id\":\"17432735\",\"last_activity_date\":\"2021-03-30T09:21:50Z\"}]}");
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


