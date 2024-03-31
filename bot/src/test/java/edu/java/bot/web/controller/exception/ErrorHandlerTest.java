package edu.java.bot.web.controller.exception;

import com.giffing.bucket4j.spring.boot.starter.context.properties.Bucket4JBootProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"bucket4j.enabled=false"})
public class ErrorHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Bucket4JBootProperties bucket4JBootProperties;

    @Test
    public void testHandleInvalidParameters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/updates")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Некорректные параметры запроса"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorId").exists());
    }

    @Test
    public void testHandleValidParameters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .post("/updates")
            .content("{\n" +
                "  \"id\": 0,\n" +
                "  \"url\": \"string\",\n" +
                "  \"description\": \"string\",\n" +
                "  \"typeOfUpdate\": \"string\",\n" +
                "  \"tgChatIds\": [\n" +
                "    0\n" +
                "  ]\n" +
                "}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
