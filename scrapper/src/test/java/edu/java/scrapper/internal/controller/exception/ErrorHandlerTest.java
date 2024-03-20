package edu.java.scrapper.internal.controller.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ErrorHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHandleNoSuchElementException() throws Exception {
    }
}
