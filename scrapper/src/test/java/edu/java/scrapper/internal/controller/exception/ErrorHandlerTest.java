package edu.java.scrapper.internal.controller.exception;

import edu.java.scrapper.db.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ErrorHandlerTest extends IntegrationEnvironment {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHandleNoSuchElementException() throws Exception {
        mockMvc.perform(delete("/tg-chat/1"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.description").value("Чат с ID 1 не найден"))
            .andExpect(jsonPath("$.errorId").exists());
    }
}
